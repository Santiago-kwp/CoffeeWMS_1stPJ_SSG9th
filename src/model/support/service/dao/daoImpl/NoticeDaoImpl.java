package model.support.service.dao.daoImpl;

import config.DBUtil;
import domain.support.Notice;
import model.support.service.dao.NoticeDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoticeDaoImpl implements NoticeDAO {
    private Connection conn;
    List<Notice> noticeList = new ArrayList<>();

    // 공지사항 생성 -------------------------------------------------------------------------------------------------------
    public boolean createNotice(Notice notice) {
        conn = DBUtil.getConnection();

        String sql = "CALL create_notice(?,?,?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {

            cStmt.setString(1, notice.getNoticeTitle());
            cStmt.setString(2, notice.getNoticeContent());
            cStmt.setBoolean(3, notice.isNoticeFixed());
            cStmt.setString(4, notice.getNoticeManagerId());

            int affected = cStmt.executeUpdate();

            if (affected == 0) return false;

            boolean pass = affected > 0;

            try (ResultSet rs = cStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int newNoticeId = rs.getInt("notice_id");
                    notice.setNoticeId(newNoticeId);
                    noticeList.add(notice);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return pass;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 공지사항 조회 (메인화면) ----------------------------------------------------------------------------------------------
    public List<Notice> readNoticeMain() {
        noticeList.clear();

        conn = DBUtil.getConnection();

        String sql = "CALL read_notice_main()";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Notice notice = new Notice();
                    notice.setNoticeDate(rs.getDate(1));
                    notice.setNoticeTitle(rs.getString(2));
                    noticeList.add(notice);
                }
            }
            return noticeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 공지사항 전항목 조회 -------------------------------------------------------------------------------------------------
    public List<Notice> readNoticeAll() {
        noticeList.clear();

        conn = DBUtil.getConnection();

        String sql = "CALL read_notice_all()";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Notice notice = new Notice();
                    notice.setNoticeId(rs.getInt(1));
                    notice.setNoticeDate(rs.getDate(2));
                    notice.setNoticeTitle(rs.getString(3));
                    notice.setNoticeContent(rs.getString(4));
                    noticeList.add(notice);
                }
            }
            return noticeList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 공지사항 상세 조회 ---------------------------------------------------------------------------------------------------
    public Notice readNoticeOne(Integer noticeId) {

        conn = DBUtil.getConnection();

        Notice oneNotice = new Notice();

        String sql = "CALL read_notice_one(?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {

            cStmt.setInt(1, noticeId);

            ResultSet rs = cStmt.executeQuery();
            if (rs.next()) {
                oneNotice.setNoticeDate(rs.getDate(1));
                oneNotice.setNoticeTitle(rs.getString(2));
                oneNotice.setNoticeContent(rs.getString(3));
                return oneNotice;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // 공지사항 수정 -------------------------------------------------------------------------------------------------------
    public boolean updateNotice(Notice notice) {

        conn = DBUtil.getConnection();

        String sql = "CALL update_notice(?,?,?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, notice.getNoticeId());
            cStmt.setString(2, notice.getNoticeTitle());
            cStmt.setString(3, notice.getNoticeContent());
            cStmt.setBoolean(4, notice.isNoticeFixed());
            cStmt.setString(5, notice.getNoticeManagerId());

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 공지사항 삭제 -------------------------------------------------------------------------------------------------------
    public boolean deleteNotice(Integer noticeId, String noticeManagerId) {

        conn = DBUtil.getConnection();

        String sql = "CALL delete_notice(?,?)";

        try (CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, noticeId);
            cStmt.setString(2, noticeManagerId);

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

//    // 공지사항 검사 -------------------------------------------------------------------------------------------------------
//    public boolean noticeTest(Integer noticeId) {
//        conn = DBUtil.getConnection();
//        String sql = "CALL notice_test(?)";
//        try (CallableStatement cStmt = conn.prepareCall(sql)) {
//            cStmt.setInt(1, noticeId);
//
//            int pass = cStmt.executeUpdate();
//
//            if (pass > 0) return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
}
