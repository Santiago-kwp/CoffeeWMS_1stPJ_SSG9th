package model.support.service.dao.daoImpl;

import config.DBUtil;
import domain.support.Inquiry;
import model.support.service.dao.InquiryDAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InquiryDaoImpl implements InquiryDAO {
    private Connection conn;
    List<Inquiry> inquiryList = new ArrayList<>();
    List<Inquiry> inquiryCategoryList = new ArrayList<>();


    // 1:1문의 생성 -------------------------------------------------------------------------------------------------------
    public boolean createInquiry(Inquiry inquiry) {
        conn = DBUtil.getConnection();

        String sql = "CALL create_inquiry(?,?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {

            cStmt.setInt(1, inquiry.getInquiryCategoryId());
            cStmt.setString(2, inquiry.getInquiryContent());
            cStmt.setString(3, inquiry.getInquiryMemberId());

            int affected = cStmt.executeUpdate();

            if (affected == 0) return false;

            boolean pass = affected > 0;

            try (ResultSet rs = cStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int newInquiryId = rs.getInt("inquiry_id");
                    inquiry.setInquiryId(newInquiryId);
                    inquiryList.add(inquiry);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return pass;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 1:1문의 회원 전체 조회 ----------------------------------------------------------------------------------------------
    public List<Inquiry> readInquiryMemberAll(String inquiryMemberId) {
        inquiryList.clear();

        conn = DBUtil.getConnection();

        String sql = "CALL read_inquiry_member_all(?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setString(1, inquiryMemberId);
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Inquiry inquiry = new Inquiry();
                    inquiry.setInquiryId(rs.getInt(1));
                    inquiry.setInquiryDate(rs.getDate(2));
                    inquiry.setInquiryCategoryName(rs.getString(3));
                    inquiry.setInquiryContent(rs.getString(4));

                    Inquiry.inquiryStatus status = Inquiry.inquiryStatus.valueOf(rs.getString(5));
                    inquiry.setInquiryStatus(status);

                    if (status == Inquiry.inquiryStatus.DONE) {
                        inquiry.setReplyDate(rs.getDate(6));
                        inquiry.setReplyContent(rs.getString(7));
                    }
                    inquiryList.add(inquiry);
                }
            }
            return inquiryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 1:1문의 총관리자 전체 조회 ----------------------------------------------------------------------------------------------
    public List<Inquiry> readInquiryManagerAll() {
        inquiryList.clear();

        conn = DBUtil.getConnection();

        String sql = "CALL read_inquiry_manager_all()";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Inquiry inquiry = new Inquiry();
                    inquiry.setInquiryId(rs.getInt(1));
                    inquiry.setInquiryDate(rs.getDate(2));
                    inquiry.setInquiryCategoryName(rs.getString(3));
                    inquiry.setInquiryContent(rs.getString(4));

                    Inquiry.inquiryStatus status = Inquiry.inquiryStatus.valueOf(rs.getString(5));
                    inquiry.setInquiryStatus(status);

                    if (status == Inquiry.inquiryStatus.DONE) {
                        inquiry.setReplyDate(rs.getDate(6));
                        inquiry.setReplyContent(rs.getString(7));
                    }
                    inquiryList.add(inquiry);
                }
            }
            return inquiryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 1:1문의 회원 상세 조회 ----------------------------------------------------------------------------------------------
    public Inquiry readInquiryMemberOne(String inquiryMemberId, Integer inquiryId) {

        conn = DBUtil.getConnection();

        String sql = "CALL read_inquiry_member_one(?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, inquiryId);
            cStmt.setString(2, inquiryMemberId);
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    Inquiry inquiry = new Inquiry();
                    inquiry.setInquiryId(rs.getInt(1));
                    inquiry.setInquiryDate(rs.getDate(2));
                    inquiry.setInquiryCategoryName(rs.getString(3));
                    inquiry.setInquiryContent(rs.getString(4));

                    Inquiry.inquiryStatus status = Inquiry.inquiryStatus.valueOf(rs.getString(5));
                    inquiry.setInquiryStatus(status);

                    if (status == Inquiry.inquiryStatus.DONE) {
                        inquiry.setReplyDate(rs.getDate(6));
                        inquiry.setReplyContent(rs.getString(7));
                    }
                    return inquiry;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 1:1문의 회원 상세 조회 ----------------------------------------------------------------------------------------------
    public Inquiry readInquiryManagerOne(Integer inquiryId) {

        conn = DBUtil.getConnection();

        String sql = "CALL read_inquiry_manager_one(?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, inquiryId);
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                if (rs.next()) {
                    Inquiry inquiry = new Inquiry();
                    inquiry.setInquiryId(rs.getInt(1));
                    inquiry.setInquiryDate(rs.getDate(2));
                    inquiry.setInquiryCategoryName(rs.getString(3));
                    inquiry.setInquiryContent(rs.getString(4));

                    Inquiry.inquiryStatus status = Inquiry.inquiryStatus.valueOf(rs.getString(5));
                    inquiry.setInquiryStatus(status);

                    if (status == Inquiry.inquiryStatus.DONE) {
                        inquiry.setReplyDate(rs.getDate(6));
                        inquiry.setReplyContent(rs.getString(7));
                    }
                    return inquiry;
                }
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 1:1문의 회원 수정 -------------------------------------------------------------------------------------------------------
    public boolean updateInquiryMember(Inquiry inquiry) {

        conn = DBUtil.getConnection();

        String sql = "CALL update_inquiry_member(?,?,?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, inquiry.getInquiryId());
            cStmt.setInt(2, inquiry.getInquiryCategoryId());
            cStmt.setString(3, inquiry.getInquiryContent());
            cStmt.setString(4, inquiry.getInquiryMemberId());

            int pass = cStmt.executeUpdate();

            if (pass > 0) {
                int index = inquiryList.indexOf(inquiry);
                inquiryList.set(index, inquiry);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 1:1문의 총관리자 수정(답변) -------------------------------------------------------------------------------------------------------
    public boolean updateInquiryManager(Inquiry inquiry) {

        conn = DBUtil.getConnection();

        String sql = "CALL update_inquiry_manager(?,?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, inquiry.getInquiryId());
            cStmt.setString(2, inquiry.getReplyContent());
            cStmt.setString(3, inquiry.getInquiryManagerId());

            int pass = cStmt.executeUpdate();

            if (pass > 0) {
                int index = inquiryList.indexOf(inquiry);
                inquiryList.set(index, inquiry);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 1:1문의 회원 삭제 -------------------------------------------------------------------------------------------------------
    public boolean deleteInquiryMember(Integer inquiryId, String inquiryMemberId) {

        conn = DBUtil.getConnection();

        String sql = "CALL delete_inquiry_member(?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, inquiryId);
            cStmt.setString(2, inquiryMemberId);

            int pass = cStmt.executeUpdate();

            if (pass > 0) {
                Inquiry inquiry = readInquiryManagerOne(inquiryId);
                inquiryList.remove(inquiry);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 1:1문의 총관리자 삭제 -------------------------------------------------------------------------------------------------------
    public boolean deleteInquiryManager(Integer inquiryId) {

        conn = DBUtil.getConnection();

        String sql = "CALL delete_inquiry_manager(?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, inquiryId);

            int pass = cStmt.executeUpdate();

            if (pass > 0) {
                Inquiry inquiry = readInquiryManagerOne(inquiryId);
                inquiryList.remove(inquiry);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    // 1:1 문의 카테고리 항목 조회 -------------------------------------------------------------------------------------------------
    public List<Inquiry> readFaqCategory() {
        inquiryCategoryList.clear();

        conn = DBUtil.getConnection();

        String sql = "CALL read_inquiry_category()";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Inquiry inquiry = new Inquiry();
                    inquiry.setInquiryCategoryId(rs.getInt(1));
                    inquiry.setInquiryCategoryName(rs.getString(2));
                    inquiryCategoryList.add(inquiry);
                }
            }
            return inquiryCategoryList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}