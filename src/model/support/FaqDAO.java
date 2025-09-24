package model.support;

import config.DBUtil;
import domain.support.Faq;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FaqDAO {
    private Connection conn;
    List<Faq> faqList = new ArrayList<>();
    List<Faq> faqCategoryList = new ArrayList<>();

    // FAQ 생성 -------------------------------------------------------------------------------------------------------
    public boolean createFaq(Faq faq) {
        conn = DBUtil.getConnection();

        String sql = "CALL create_faq(?,?,?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {

            cStmt.setInt(1, faq.getFaqCategoryId());
            cStmt.setString(2, faq.getFaqQuestion());
            cStmt.setString(3, faq.getFaqReply());
            cStmt.setString(4, faq.getFaqManagerId());

            int affected = cStmt.executeUpdate();

            if (affected == 0) return false;

            boolean pass = affected > 0;

            try (ResultSet rs = cStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int newFaqId = rs.getInt("faq_id");
                    faq.setFaqId(newFaqId);
                    faqList.add(faq);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return pass;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // FAQ 전체 조회 -------------------------------------------------------------------------------------------------
    public List<Faq> readFaqAll() {
        faqList.clear();

        conn = DBUtil.getConnection();

        String sql = "CALL read_faq_all()";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Faq faq = new Faq();
                    faq.setFaqId(rs.getInt(1));
                    faq.setFaqDate(rs.getDate(2));
                    faq.setFaqCategoryName(rs.getString(3));
                    faq.setFaqQuestion(rs.getString(4));
                    faq.setFaqReply(rs.getString(5));
                    faqList.add(faq);
                }
            }
            return faqList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // FAQ 상세 조회 ---------------------------------------------------------------------------------------------------
    public Faq readFaqOne(Integer faqId) {

        conn = DBUtil.getConnection();

        String sql = "CALL read_faq_one(?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {

            cStmt.setInt(1, faqId);

            ResultSet rs = cStmt.executeQuery();
            if (rs.next()) {
                Faq oneFaq = new Faq();
                oneFaq.setFaqDate(rs.getDate(1));
                oneFaq.setFaqCategoryName(rs.getString(2));
                oneFaq.setFaqQuestion(rs.getString(3));
                oneFaq.setFaqReply(rs.getString(4));
                return oneFaq;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // FAQ 수정 -------------------------------------------------------------------------------------------------------
    public boolean updateFaq(Faq faq) {

        conn = DBUtil.getConnection();

        String sql = "CALL update_faq(?,?,?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, faq.getFaqId());
            cStmt.setString(2, faq.getFaqQuestion());
            cStmt.setString(3, faq.getFaqReply());

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // FAQ 삭제 -------------------------------------------------------------------------------------------------------
    public boolean deleteFaq(Integer faqId, String faqManagerId) {

        conn = DBUtil.getConnection();

        String sql = "CALL delete_faq(?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, faqId);
            cStmt.setString(2, faqManagerId);

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // FAQ 카테고리 항목 조회 -------------------------------------------------------------------------------------------------
    public List<Faq> readFaqCategory() {
        faqCategoryList.clear();

        conn = DBUtil.getConnection();

        String sql = "CALL read_faq_category()";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Faq faq = new Faq();
                    faq.setFaqCategoryId(rs.getInt(1));
                    faq.setFaqCategoryName(rs.getString(2));
                    faqCategoryList.add(faq);
                }
            }
            return faqCategoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
