package service.support.readService.readImpl;

import constant.support.BoardText;
import domain.support.Faq;
import model.support.dao.daoImpl.FaqDaoImpl;
import service.support.readService.FaqRead;

import java.util.List;

public class FaqReadImpl implements FaqRead {
    FaqDaoImpl faqDAO = new FaqDaoImpl();

    // FAQ 전체 조회 -----------------------------------------------------------------------------------------------------
    public void faqReadAll() {
        faqDAO = new FaqDaoImpl();
        System.out.println(BoardText.FAQ_READ_ALL.getMessage());

        System.out.printf("%-5S\t | %-10S\t | %-13S\t | %-18S\t | %-15S\t\n", "NO", "날짜", "카테고리", "질문", "답변");

        System.out.println(BoardText.LINE.getMessage());

        List<Faq> readAll = faqDAO.readFaqAll();
        for (Faq faq : readAll) {

            // 내용을 10글자만 출력
            String q = faq.getFaqQuestion();
            if (q.length() > 10) q = q.substring(0, 10);

            String r = faq.getFaqReply();
            if (r.length() > 10) r = r.substring(0, 10);

            System.out.printf("%-5S\t | %-10S\t | %-13S\t | %-15S\t | %-15S\t", faq.getFaqId(), faq.getFaqDate(), faq.getFaqCategoryName(), q, r);
            System.out.println();
        }
    }

    public void faqReadOne(Faq oneFaq) {
        System.out.printf("%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n%-4s\t| %s\n",
                BoardText.CREATE_DATE.getMessage(), oneFaq.getFaqDate(),
                BoardText.CATEGORY.getMessage(), oneFaq.getFaqCategoryName(),
                BoardText.QUEST.getMessage(), oneFaq.getFaqQuestion(),
                BoardText.ANSWER.getMessage(), oneFaq.getFaqReply());
    }
}
