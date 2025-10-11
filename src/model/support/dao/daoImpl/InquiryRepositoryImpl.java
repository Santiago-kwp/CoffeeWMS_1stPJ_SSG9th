package model.support.dao.daoImpl;

import config.DBUtil;
import constant.support.BoardErrorCode;
import domain.support.Board;
import domain.support.Category;
import domain.support.Inquiry;
import model.support.dao.InquiryRepository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InquiryRepositoryImpl implements InquiryRepository {
    // 1:1 문의 생성 (회원)-------------------------------------------------------------------------------------------------
    public boolean createInquiry(Inquiry inquiry) {

        String sql = "CALL create_inquiry(?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {

            cStmt.setInt(1, inquiry.getInquiryCategoryId());
            cStmt.setString(2, inquiry.getInquiryContent());
            cStmt.setString(3, inquiry.getInquiryMemberId());

            int affected = cStmt.executeUpdate();

            if (affected == 0) return false;

            boolean pass = affected > 0;

            try (ResultSet rs = cStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    inquiry.setInquiryId(rs.getInt("inquiry_id"));
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

    // 1:1 문의 전체 조회 (회원)---------------------------------------------------------------------------------------------
    public List<Board> readInquiryMemberAll(String memberId) {
        List<Inquiry> inquiryList = new ArrayList<>();

        String sql = "CALL read_inquiry_member_all(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setString(1, memberId);
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
            List<Board> inquiryBoardList = inquiryList.stream().map(inquiry -> (Board) inquiry).toList();
            return inquiryBoardList;
        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_FOUND_LIST.getMessage());
            return null;
        }
    }

    // 1:1 문의 전체 조회 (총관리자)------------------------------------------------------------------------------------------
    public List<Board> readInquiryManagerAll() {
        List<Inquiry> inquiryList = new ArrayList<>();

        String sql = "CALL read_inquiry_manager_all()";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
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
            List<Board> inquiryBoardList = inquiryList.stream().map(inquiry -> (Board) inquiry).toList();
            return inquiryBoardList;
        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_FOUND_LIST.getMessage());
            return null;
        }
    }

    // 1:1 문의 상세 조회 (회원)---------------------------------------------------------------------------------------------
    public Board readInquiryMemberOne(String inquiryMemberId, Integer inquiryId) {

        String sql = "CALL read_inquiry_member_one(?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setString(1, inquiryMemberId);
            cStmt.setInt(2, inquiryId);

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
            System.out.println(BoardErrorCode.NOT_FOUND_BOARD.getMessage());
            return null;
        }
    }

    // 1:1 문의 상세 조회 (총관리자)------------------------------------------------------------------------------------------
    public Board readInquiryManagerOne(Integer inquiryId) {

        String sql = "CALL read_inquiry_manager_one(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
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
        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_FOUND_BOARD.getMessage());
        }
        return null;
    }

    // 1:1 문의 수정 (회원)-------------------------------------------------------------------------------------------------
    public boolean updateInquiryMember(Inquiry inquiry) {

        String sql = "CALL update_inquiry_member(?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, inquiry.getInquiryId());
            cStmt.setInt(2, inquiry.getInquiryCategoryId());
            cStmt.setString(3, inquiry.getInquiryContent());
            cStmt.setString(4, inquiry.getInquiryMemberId());

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;

        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_UPDATE_BOARD.getMessage());
        }
        return false;
    }

    // 1:1 문의 수정 (총관리자)----------------------------------------------------------------------------------------------
    public boolean updateInquiryManager(Inquiry inquiry) {

        String sql = "CALL update_inquiry_manager(?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, inquiry.getInquiryId());
            cStmt.setString(2, inquiry.getReplyContent());
            cStmt.setString(3, inquiry.getInquiryManagerId());

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;

        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_REPLY_BOARD.getMessage());
        }
        return false;
    }

    // 1:1 문의 삭제 (회원)-------------------------------------------------------------------------------------------------
    public boolean deleteInquiryMember(Integer inquiryId, String inquiryMemberId) {

        String sql = "CALL delete_inquiry_member(?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, inquiryId);
            cStmt.setString(2, inquiryMemberId);

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;

        } catch (Exception e) {
            System.out.println(BoardErrorCode.NOT_DELETE_BOARD.getMessage());
        }
        return false;
    }

    // 1:1 문의 삭제 (총관리자)----------------------------------------------------------------------------------------------
    public boolean deleteInquiryManager(Integer inquiryId) {

        String sql = "CALL delete_inquiry_manager(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, inquiryId);

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;

        } catch (Exception e) {
            System.out.println(BoardErrorCode.NOT_DELETE_BOARD.getMessage());
        }
        return false;
    }

    // 1:1 문의 카테고리 조회 -----------------------------------------------------------------------------------------------
    public List<Category> readInquiryCategory() {
        List<Category> inquiryCategoryList = new ArrayList<>();

        String sql = "CALL read_inquiry_category()";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Category inquiryCategory = new Category();
                    inquiryCategory.setCategoryId(rs.getInt(1));
                    inquiryCategory.setCategoryName(rs.getString(2));
                    inquiryCategoryList.add(inquiryCategory);
                }
            }
            return inquiryCategoryList;
        } catch (SQLException e) {
            System.out.println(BoardErrorCode.NOT_FOUND_LIST.getMessage());
            return null;
        }
    }
}