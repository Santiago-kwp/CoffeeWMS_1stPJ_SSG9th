package model.support.dao.daoImpl;

import config.DBUtil;
import constant.support.BoardErrorCode;
import domain.support.Board;
import domain.support.Notice;
import exception.support.NotFoundException;
import model.support.dao.NoticeRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoticeRepositoryImpl implements NoticeRepository {
    // 공지사항 생성 (총관리자)-----------------------------------------------------------------------------------------------
    public boolean createNotice(Notice notice) {

        String sql = "CALL create_notice(?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setString(1, notice.getNoticeTitle());
            cStmt.setString(2, notice.getNoticeContent());
            cStmt.setBoolean(3, notice.isNoticeFixed());
            cStmt.setString(4, notice.getNoticeManagerId());

            int affected = cStmt.executeUpdate();

            if (affected == 0) {
                conn.rollback();
                return false;
            }

            boolean pass = affected > 0;

            try (ResultSet rs = cStmt.getGeneratedKeys()) {
                if (rs.next()) {
                    notice.setNoticeId(rs.getInt("notice_id"));
                }
            } catch (SQLException e) {
                throw new NotFoundException(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
            }

            return pass;

        } catch (SQLException e) {
            throw new NotFoundException(BoardErrorCode.NOT_CREATE_BOARD.getMessage());
        }
    }

    // 공지사항 조회 (메인화면)-----------------------------------------------------------------------------------------------
    public List<Board> readNoticePreview() {
        List<Notice> noticeList = new ArrayList<>();

        String sql = "CALL read_notice_main()";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            ResultSet rs = cStmt.executeQuery();
            if (rs != null) {
                while (rs.next()) {
                    Notice notice = new Notice();
                    notice.setNoticeDate(rs.getDate(1));
                    notice.setNoticeTitle(rs.getString(2));
                    noticeList.add(notice);
                }
            }
            List<Board> noticeBoardList = noticeList.stream().map(notice -> (Board) notice).toList();
            return noticeBoardList;
        } catch (SQLException e) {
            throw new NotFoundException(BoardErrorCode.NOT_FOUND_LIST.getMessage());
        }
    }

    // 공지사항 전체 조회 ---------------------------------------------------------------------------------------------------
    public List<Board> readNoticeAll() {
        List<Notice> noticeList = new ArrayList<>();

        String sql = "CALL read_notice_all()";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.execute();
            ResultSet rs = cStmt.getResultSet();
            if (rs != null) {
                while (rs.next()) {
                    Notice notice = new Notice();
                    notice.setNoticeId(rs.getInt("notice_id"));
                    notice.setNoticeDate(rs.getDate("notice_date"));
                    notice.setNoticeTitle(rs.getString("notice_title"));
                    notice.setNoticeContent(rs.getString("notice_content"));
                    noticeList.add(notice);
                }
            }
            List<Board> noticeBoardList = noticeList.stream().map(notice -> (Board) notice).toList();
            return noticeBoardList;
        } catch (SQLException e) {
            throw new NotFoundException(BoardErrorCode.NOT_FOUND_LIST.getMessage());
        }
    }

    // 공지사항 상세 조회 ---------------------------------------------------------------------------------------------------
    public Board readNoticeOne(Integer noticeId) {

        Notice notice = new Notice();

        String sql = "CALL read_notice_one(?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, noticeId);

            ResultSet rs = cStmt.executeQuery();
            if (rs.next()) {
                notice.setNoticeDate(rs.getDate(1));
                notice.setNoticeTitle(rs.getString(2));
                notice.setNoticeContent(rs.getString(3));

                return notice;
            }
        } catch (SQLException e) {
            throw new NotFoundException(BoardErrorCode.NOT_FOUND_BOARD.getMessage());
        }
        return null;
    }

    // 공지사항 수정 (총관리자)-----------------------------------------------------------------------------------------------
    public boolean updateNotice(Notice notice) {

        String sql = "CALL update_notice(?,?,?,?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, notice.getNoticeId());
            cStmt.setString(2, notice.getNoticeTitle());
            cStmt.setString(3, notice.getNoticeContent());
            cStmt.setBoolean(4, notice.isNoticeFixed());
            cStmt.setString(5, notice.getNoticeManagerId());

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;

        } catch (SQLException e) {
            throw new NotFoundException(BoardErrorCode.NOT_UPDATE_BOARD.getMessage());
        }
        return false;
    }

    // 공지사항 삭제 (총관리자)-----------------------------------------------------------------------------------------------
    public boolean deleteNotice(Integer noticeId, String noticeManagerId) {

        String sql = "CALL delete_notice(?,?)";

        try (Connection conn = DBUtil.getConnection();
             CallableStatement cStmt = conn.prepareCall(sql)) {
            cStmt.setInt(1, noticeId);
            cStmt.setString(2, noticeManagerId);

            int pass = cStmt.executeUpdate();

            if (pass > 0) return true;
        } catch (Exception e) {
            throw new NotFoundException(BoardErrorCode.NOT_DELETE_BOARD.getMessage());
        }
        return false;
    }
}
