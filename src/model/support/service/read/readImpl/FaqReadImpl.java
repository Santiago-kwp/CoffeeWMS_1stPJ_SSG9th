package model.support.service.read.readImpl;

import domain.support.Faq;
import model.support.service.dao.daoImpl.FaqDaoImpl;
import model.support.service.read.FaqRead;

import java.util.List;

public class FaqReadImpl implements FaqRead {
    FaqDaoImpl faqDAO = new FaqDaoImpl();

    // FAQ 전체 조회 ---------------------------------------------------------------------------------------------------
    public void faqReadAll() {
        faqDAO = new FaqDaoImpl();
        System.out.println("\n-------------------------------<< FAQ 전체 목록 >>--------------------------------");
        System.out.printf("%-5S\t | %-10S\t | %-13S\t | %-18S\t | %-15S\t\n", "NO", "날짜", "카테고리", "질문", "답변");
        line();
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

    // 라인 -------------------------------------------------------------------------------------------------------------
    public void line() {
        System.out.println("--------------------------------------------------------------------------------");
    }
}
