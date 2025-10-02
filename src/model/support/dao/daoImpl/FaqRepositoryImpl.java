package model.support.dao.daoImpl;

import config.DBUtil;
import constant.support.BoardErrorCode;
import domain.support.Board;
import domain.support.Faq;
import domain.support.Category;
import model.support.dao.FaqRepository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FaqRepositoryImpl implements FaqRepository {
    // FAQ 생성 -------------------------------------------------------------------------------------------------------
    public boolean createFaq(Faq faq) {

        String sql = "CALL create_faq(?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {

            cStmt.setInt(1, faq.getFaqCategoryId());
            cStmt.setString(2, faq.getFaqQuestion());
            cStmt.setString(3, faq.getFaqReply());
            cStmt.setString(4, faq.getFaqManagerId());

            int affected = cStmt.executeUpdate();

            if (affected == 0) return false;

            boolean pass = affected > 0;

            try (ResultSet rs = cStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    faq.setFaqId(rs.getInt("faq_id"));
                }
            } catch (SQLException e) {
                System.out.println(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
            }
            return pass;
        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
            return false;
        }
    }

    // FAQ 전체 조회 -------------------------------------------------------------------------------------------------
    public List<Board> readFaqAll() {
        List<Faq> faqList = new ArrayList<>();

        String sql = "CALL read_faq_all()";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
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
            List<Board> faqBoardList = faqList.stream().map(faq -> (Board) faq).toList() ;
            return faqBoardList;
        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_FOUND_LIST.getMessage());
            return null;
        }
    }

    // FAQ 상세 조회 ---------------------------------------------------------------------------------------------------
    public Board readFaqOne(Integer faqId) {

        String sql = "CALL read_faq_one(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {

            cStmt.setInt(1, faqId);

            ResultSet rs = cStmt.executeQuery();
            if (rs.next()) {
                Faq faq = new Faq();
                faq.setFaqDate(rs.getDate(1));
                faq.setFaqCategoryName(rs.getString(2));
                faq.setFaqQuestion(rs.getString(3));
                faq.setFaqReply(rs.getString(4));

                return faq;
            }
        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_FOUND_BOARD.getMessage());
        } return null;
    }

    // FAQ 수정 -------------------------------------------------------------------------------------------------------
    public boolean updateFaq(Faq faq) {

        String sql = "CALL update_faq(?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, faq.getFaqId());
            cStmt.setString(2, faq.getFaqQuestion());
            cStmt.setString(3, faq.getFaqReply());
            cStmt.setString(4, faq.getFaqManagerId());

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;

        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_UPDATE_BOARD.getMessage());
        }
        return false;
    }

    // FAQ 삭제 -------------------------------------------------------------------------------------------------------
    public boolean deleteFaq(Integer faqId, String faqManagerId) {

        String sql = "CALL delete_faq(?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, faqId);
            cStmt.setString(2, faqManagerId);

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;

        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_DELETE_BOARD.getMessage());
        }
        return false;
    }

    // FAQ 카테고리 조회 ---------------------------------------------------------------------------------------------------
    public List<Category> readFaqCategory() {
        List<Category> faqCategoryList = new ArrayList<>();

        String sql = "CALL read_faq_category()";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Category faqCategory = new Category();
                    faqCategory.setCategoryId(rs.getInt(1));
                    faqCategory.setCategoryName(rs.getString(2));
                    faqCategoryList.add(faqCategory);
                }
            }
            return faqCategoryList;
        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_FOUND_LIST.getMessage());
            return null;
        }
    }
}
