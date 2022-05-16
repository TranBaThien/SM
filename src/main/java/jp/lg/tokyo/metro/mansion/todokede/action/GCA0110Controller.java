/*
 * @(#) GCA0110Controller.java
 *
 * Copyright(C) 2019 by Hitachi Information Systems CO., LTD
 *
 * Description:
 * @author tbthien
 * Create Date: 2019/11/25
 * Version: 1.0
 */

package jp.lg.tokyo.metro.mansion.todokede.action;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.lg.tokyo.metro.mansion.todokede.common.CommonConstants;
import jp.lg.tokyo.metro.mansion.todokede.common.annotation.AllowMaintenance;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.CommonUtils;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.MessageUtil;
import jp.lg.tokyo.metro.mansion.todokede.common.utils.SessionUtil;
import jp.lg.tokyo.metro.mansion.todokede.form.SearchForm;
import jp.lg.tokyo.metro.mansion.todokede.logic.ICityLogic;
import jp.lg.tokyo.metro.mansion.todokede.logic.IManagementAssociationLogic;
import jp.lg.tokyo.metro.mansion.todokede.vo.GCA0110Vo;

/**
 * @author tbthien
 *
 */
@Controller
@AllowMaintenance
public class GCA0110Controller {

    private static final Logger logger = LogManager.getLogger(GCA0110Controller.class);
    private static final String STARTDATE = "登録日－開始";
    private static final String ENDDATE = "登録日－終了";
    private static final String RESULT = "審査結果";
    private static final String MANAGEMENTASSOCIATIONINFO = "新規ユーザ情報";

    @Autowired
    private ICityLogic citylogic;

    @Autowired
    private IManagementAssociationLogic managementassociationlogic;

    
    /**
     * @param model Model
     * @param page Integer
     * @param size Integer
     * @param request HttpServletRequest
     * @return String
     */
    @PostMapping("/GCA0110")
    public String show(Model model, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size,
            HttpServletRequest request) {

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));

        List<String> errorMessage = new ArrayList<>();
        /*
         * If total number of pages on the screen is too large, only display pages in
         * the range of startIndex to finishIndex
         */
        int startIndex;
        int finishIndex;
        /*
         * This flag determines whether the search result list section is displayed or
         * not if isDisplay = false, List of search results will not be displayed if
         * isDisplay = true, List of search results will be displayed
         */
        boolean isDisplay;
        /*
         * There are two paging sections on the GCA0110.html file corresponding to the
         * show and searchManagementAssociation functions This flag indicates which
         * pagination to use in this case if isSearching = 0, That means the pagination
         * corresponding to the path /GCA0110 is being used if isSearching = 1, That
         * means the pagination corresponding to the path /GCA0110/search is being used
         */
        int isSearching;
        // list contain applycation number to add to session
        List<String> listapplyno = new ArrayList<>();
        // get GCA0110_SEARCH_MAX from session
        int searchMax = Integer.parseInt(SessionUtil.getSystemSettingByKey("GCA0110_SEARCH_MAX"));
        // get GCA0110_LIST_DISPLAY_MAX from session
        size = Integer.parseInt(SessionUtil.getSystemSettingByKey("GCA0110_LIST_DISPLAY_MAX"));

        SearchForm form = new SearchForm();
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1030_I, form.toString()));
        // get a page of search result list with corresponding page, size and citycode
        Page<GCA0110Vo> management = managementassociationlogic.getListManagementAssociation(page - 1, size);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1040_I, String.valueOf(management.getContent().size())));
        // get total search results
        int count = managementassociationlogic.totalResultWhenDisplay(page - 1, size);
        // total of pages
        int totalPage;
              
        // create list of application number
        for (GCA0110Vo vo : management.getContent()) {
            listapplyno.add(vo.getApplicationNo());
        }
        
        HttpSession session = request.getSession();
        // save information to session
        session.setAttribute("CityCode", "");
        session.setAttribute("ApartmentName", "");
        session.setAttribute("StartTimeApply", "");
        session.setAttribute("EndTimeApply", "");
        session.setAttribute("Unexamined", "on");
        session.setAttribute("Approval", CommonConstants.OFF);
        session.setAttribute("Reject", CommonConstants.OFF);
        session.setAttribute("Page", page);
        session.setAttribute("listapplynumber", listapplyno);
        session.setAttribute("count", count);
        
        if (count > searchMax) { 
            // error message
            errorMessage.add(
                    MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0127), new Object[] { searchMax }));

            totalPage = 1;
            startIndex = Math.max(page - 5, 1);
            finishIndex = Math.min(page + 5, totalPage);
            isSearching = 0;
            isDisplay = false;            
            form.setUnexamined(CommonConstants.ON);

            /*
             * The list of sequence numbers corresponds to each element in search result
             */
            model.addAttribute("Listno", getListNumber(page, size, count));
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("Count", CommonUtils.formatPrice(Integer.toString(count)));
            model.addAttribute("IsDisplay", isDisplay);
            model.addAttribute("IsSearching", isSearching);
            model.addAttribute("messageError", errorMessage);
            model.addAttribute("Form", form);
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("startIndex", startIndex);
            model.addAttribute("finishIndex", finishIndex);

            /*
             * the list of city name corresponding to city code
             */
            model.addAttribute("ListCity", citylogic.getListCity());
            model.addAttribute("managementassociation", management);

            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return "GCA0110";
        }
        if (count == 0) {            
            // error message
            errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0145),
                    new Object[] { MANAGEMENTASSOCIATIONINFO }));

            totalPage = 1;
            startIndex = Math.max(page - 5, 1);
            finishIndex = Math.min(page + 5, totalPage);
            isSearching = 0;
            isDisplay = false;
            form.setUnexamined(CommonConstants.ON);

            model.addAttribute("Listno", getListNumber(page, size, count));
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("Count", CommonUtils.formatPrice(Integer.toString(count)));
            model.addAttribute("IsDisplay", isDisplay);
            model.addAttribute("IsSearching", isSearching);
            model.addAttribute("messageError", errorMessage);
            model.addAttribute("Form", form);
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("startIndex", startIndex);
            model.addAttribute("finishIndex", finishIndex);
            model.addAttribute("ListCity", citylogic.getListCity());
            model.addAttribute("managementassociation", management);

            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return "GCA0110";
        }       

        // Calculate the total number of pages
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }
        
        form.setUnexamined(CommonConstants.ON);
        startIndex = Math.max(page - 5, 1);
        finishIndex = Math.min(page + 5, totalPage);
        isSearching = 0;
        isDisplay = true;

        model.addAttribute("Listno", getListNumber(page, size, count));
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("Count", CommonUtils.formatPrice(Integer.toString(count)));
        model.addAttribute("IsDisplay", isDisplay);
        model.addAttribute("IsSearching", isSearching);
        model.addAttribute("Form", form);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("startIndex", startIndex);
        model.addAttribute("finishIndex", finishIndex);
        model.addAttribute("ListCity", citylogic.getListCity());
        model.addAttribute("managementassociation", management);

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return "GCA0110"; 
    }

    
    /**
     * @param form SearchForm
     * @param result BindingResult
     * @param model Model
     * @param request HttpServletRequest
     * @param page Integer
     * @param size Integer
     * @return String
     */
    @PostMapping("/GCA0110/search")
    public String searchManagementAssociation(@ModelAttribute("Form") @Valid SearchForm form, BindingResult result,
            Model model, HttpServletRequest request,
            @RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "5") Integer size) {

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        List<String> errorMessage = new ArrayList<String>();

        /*
         * There are two paging sections on the GCA0110.html file corresponding to the
         * show and searchManagementAssociation functions This flag indicates which
         * pagination to use in this case if isSearching = 0, That means the pagination
         * corresponding to the path /GCA0110 is being used if isSearching = 1, That
         * means the pagination corresponding to the path /GCA0110/search is being used
         */
        int isSearching;
        HttpSession session = request.getSession();
        Page<GCA0110Vo> management;
        boolean notPaging = true;
        // list contain applycation number to add to session
        List<String> listapplyno = new ArrayList<String>();

        /*
         * This flag determines whether the search result list section is displayed or
         * not if isDisplay = false, List of search results will not be displayed if
         * isDisplay = true, List of search results will be displayed
         */
        boolean isDisplay;
        int count;

        /*
         * If total number of pages on the screen is too large, only display pages in
         * the range of startIndex to finishIndex
         */
        int startIndex;
        int finishIndex;
        // total of pages
        int totalPage;

        /*
         * Maximum number of search results
         */
        int searchMax = Integer.parseInt(SessionUtil.getSystemSettingByKey("GCA0110_SEARCH_MAX"));
        size = Integer.parseInt(SessionUtil.getSystemSettingByKey("GCA0110_LIST_DISPLAY_MAX"));

        // NullPointerException error prevention
        if (form.getUnexamined() == null) {
            form.setUnexamined(CommonConstants.OFF);
        }
        if (form.getApproval() == null) {
            form.setApproval(CommonConstants.OFF);
        }
        if (form.getReject() == null) {
            form.setReject(CommonConstants.OFF);
        }

        /*
         * check if user want to get another page of search result
         * set notPaging = false because we don't need to Check (single
         * item) and Check (correlation)
         */
        if (form.getApartmentName() == null) {
            notPaging = false; 
        }

        /*
         * get error message form BindingResult Check (single item)
         */
        if (result.hasErrors() && notPaging) {
            CommonUtils.getErrorsFromBindingResult(result, errorMessage);
        }

        /*
         * get error message form BindingResult Check (correlation)
         */
        if (errorMessage.isEmpty() && notPaging) {           
            /*
             * Check (correlation) when StartTimeApply and EndTimeApply are entered
             */
            if (!CommonConstants.BLANK.equals(form.getStartTimeApply()) && !CommonConstants.BLANK.equals(form.getEndTimeApply())) {
                checkCorrelationRegistrationDate(form, errorMessage);
            }
            checkCorrelationExaminationResult(form, errorMessage);
        }

        /*
         * Display message error when Check (single item) or Check (correlation)
         */
        if (!errorMessage.isEmpty()) {
            SearchForm form2 = new SearchForm();
            
            // get form information from session
            form2.setCityCode(session.getAttribute("CityCode").toString());
            form2.setApartmentName(session.getAttribute("ApartmentName").toString());
            form2.setStartTimeApply(session.getAttribute("StartTimeApply").toString());
            form2.setEndTimeApply(session.getAttribute("EndTimeApply").toString());
            form2.setUnexamined(session.getAttribute("Unexamined").toString());
            form2.setApproval(session.getAttribute("Approval").toString());
            form2.setReject(session.getAttribute("Reject").toString());
                
            page = Integer.parseInt(session.getAttribute("Page").toString());
            management = managementassociationlogic.searchManagementAssociation(page - 1, size, form2);
            // total search results
            count = managementassociationlogic.TotalSearchResults(form2);

            // Calculate the total number of pages
            if (count % size == 0) {
                totalPage = count / size;
            } else {
                totalPage = count / size + 1;
            }
            
            if (count == 0 || count > searchMax) {
                isDisplay = false; 
                totalPage = 1;
            } else {
                isDisplay = true;
            }                      
            
            startIndex = Math.max(page - 5, 1);
            finishIndex = Math.min(page + 5, totalPage);
            isSearching = 1;            
                       
            /*
             * The list of sequence numbers corresponds to each element in search result
             */
            model.addAttribute("Listno", getListNumber(page, size, count));
            model.addAttribute("totalPage", totalPage);
            model.addAttribute("Count", CommonUtils.formatPrice(Integer.toString(count)));
            model.addAttribute("IsDisplay", isDisplay);
            model.addAttribute("IsSearching", isSearching);
            model.addAttribute("messageError", errorMessage);
            model.addAttribute("Form", form);
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("startIndex", startIndex);
            model.addAttribute("finishIndex", finishIndex);
            model.addAttribute("ListCity", citylogic.getListCity());
            model.addAttribute("managementassociation", management);

            logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
            return "GCA0110";
        }

        // get search conditions from session
        if (!notPaging) {
            form.setCityCode(session.getAttribute("CityCode").toString());
            form.setApartmentName(session.getAttribute("ApartmentName").toString());
            form.setStartTimeApply(session.getAttribute("StartTimeApply").toString());
            form.setEndTimeApply(session.getAttribute("EndTimeApply").toString());
            form.setUnexamined(session.getAttribute("Unexamined").toString());
            form.setApproval(session.getAttribute("Approval").toString());
            form.setReject(session.getAttribute("Reject").toString());
        }

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1030_I, form.toString()));
        isSearching = 1;

        /*
         * Get a page of search result corresponding to page and size and form
         */
        management = managementassociationlogic.searchManagementAssociation(page - 1, size, form);

        // Get total search results number
        count = managementassociationlogic.TotalSearchResults(form);
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1040_I, String.valueOf(management.getContent().size())));
        // create a list of applycation number
        for (GCA0110Vo vo : management.getContent()) {
            listapplyno.add(vo.getApplicationNo());
        }

        // save search condition, page, and list of applycation number to session
        session.setAttribute("CityCode", form.getCityCode());
        session.setAttribute("ApartmentName", form.getApartmentName());
        session.setAttribute("StartTimeApply", form.getStartTimeApply());
        session.setAttribute("EndTimeApply", form.getEndTimeApply());
        session.setAttribute("Unexamined", form.getUnexamined());
        session.setAttribute("Approval", form.getApproval());
        session.setAttribute("Reject", form.getReject());
        session.setAttribute("Page", page);
        session.setAttribute("listapplynumber", listapplyno);
        session.setAttribute("count", count);
        
        // if there is no error when Check (single item) or Check (correlation)
        if (errorMessage.isEmpty()) {
            if (count > searchMax) {
                // error message
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0127),
                        new Object[] { searchMax }));

                startIndex = Math.max(page - 5, 1);
                finishIndex = Math.min(page + 5, management.getTotalPages());
                isDisplay = false;
                totalPage = 1;

                model.addAttribute("Listno", getListNumber(page, size, count));
                // Total search results
                model.addAttribute("Count", CommonUtils.formatPrice(Integer.toString(count)));
                model.addAttribute("IsDisplay", isDisplay);
                model.addAttribute("totalPage", totalPage);
                model.addAttribute("IsSearching", isSearching);
                model.addAttribute("messageError", errorMessage);
                model.addAttribute("Form", form);
                model.addAttribute("page", page);
                model.addAttribute("size", size);
                model.addAttribute("startIndex", startIndex);
                model.addAttribute("finishIndex", finishIndex);
                model.addAttribute("ListCity", citylogic.getListCity());
                model.addAttribute("managementassociation", management);

                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return "GCA0110";
            }
            if (count == 0) {
                // error message
                errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0145),
                        new Object[] { MANAGEMENTASSOCIATIONINFO }));

                startIndex = Math.max(page - 5, 1);
                finishIndex = Math.min(page + 5, management.getTotalPages());
                isDisplay = false;
                totalPage = 1;

                model.addAttribute("Listno", getListNumber(page, size, count));
                // Total search results
                model.addAttribute("Count", CommonUtils.formatPrice(Integer.toString(count)));
                model.addAttribute("totalPage", totalPage);
                model.addAttribute("IsDisplay", isDisplay);
                model.addAttribute("IsSearching", isSearching);
                model.addAttribute("messageError", errorMessage);
                model.addAttribute("Form", form);
                model.addAttribute("page", page);
                model.addAttribute("size", size);
                model.addAttribute("startIndex", startIndex);
                model.addAttribute("finishIndex", finishIndex);
                model.addAttribute("ListCity", citylogic.getListCity());
                model.addAttribute("managementassociation", management);

                logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
                return "GCA0110";
            }
        }
       
        // Calculate the total number of pages
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }

        startIndex = Math.max(page - 5, 1);
        finishIndex = Math.min(page + 5, totalPage);
        isDisplay = true;

        model.addAttribute("Listno", getListNumber(page, size, count));
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("Count", CommonUtils.formatPrice(Integer.toString(count)));
        model.addAttribute("IsDisplay", isDisplay);
        model.addAttribute("IsSearching", isSearching);
        model.addAttribute("Form", form);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("startIndex", startIndex);
        model.addAttribute("finishIndex", finishIndex);
        model.addAttribute("ListCity", citylogic.getListCity());
        model.addAttribute("managementassociation", management);

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return "GCA0110";

    }

    /**
     * @param model Model
     * @param request HttpServletRequest
     * @return String
     */
    @GetMapping("/GCA0110/return")
    public String returnFromDesinationScreen(Model model, HttpServletRequest request) {
        
        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1010_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        HttpSession session = request.getSession();
        SearchForm form = new SearchForm();

        // get search conditions from session
        form.setCityCode(session.getAttribute("CityCode").toString());
        form.setApartmentName(session.getAttribute("ApartmentName").toString());
        form.setStartTimeApply(session.getAttribute("StartTimeApply").toString());
        form.setEndTimeApply(session.getAttribute("EndTimeApply").toString());
        form.setUnexamined(session.getAttribute("Unexamined").toString());
        form.setApproval(session.getAttribute("Approval").toString());
        form.setReject(session.getAttribute("Reject").toString());
        int count = (int)session.getAttribute("count");
        
        List<String> listapplyno = (List<String>) session.getAttribute("listapplynumber");
        
        int page = Integer.parseInt(session.getAttribute("Page").toString());
        int size = Integer.parseInt(SessionUtil.getSystemSettingByKey("GCA0110_LIST_DISPLAY_MAX"));
        
        int totalPage;
        // Calculate the total number of pages
        if (count % size == 0) {
            totalPage = count / size;
        } else {
            totalPage = count / size + 1;
        }
        
        int startIndex;
        startIndex = Math.max(page - 5, 1);
        
        int finishIndex;
        finishIndex = Math.min(page + 5, totalPage);
        
        boolean isDisplay;
        isDisplay = true;
              
        model.addAttribute("Listno", getListNumberAgain(page - 1, size, listapplyno.size()));
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("Count", CommonUtils.formatPrice(Integer.toString(count)));
        model.addAttribute("IsDisplay", isDisplay);
        
        int isSearching = 1;
        model.addAttribute("IsSearching", isSearching);
        model.addAttribute("Form", form);
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("startIndex", startIndex);
        model.addAttribute("finishIndex", finishIndex);
        model.addAttribute("ListCity", citylogic.getListCity());
        
        Page<GCA0110Vo> management = managementassociationlogic.getListManagementAssociationAgain(page - 1, size, form, listapplyno);
        model.addAttribute("managementassociation", management);

        logger.info(MessageUtil.getMessage(CommonConstants.LOG_LG1020_I, Thread.currentThread().getStackTrace()[1].getMethodName()));
        return "GCA0110";

    }

    private void checkCorrelationRegistrationDate(SearchForm form, List<String> errorMessage) {

        String startdate = form.getStartTimeApply();
        String enddate = form.getEndTimeApply();

        // convert String to LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CommonConstants.SLASH_YYYYMMDD);
        LocalDate starttime = LocalDate.parse(startdate, formatter);
        LocalDate endtime = LocalDate.parse(enddate, formatter);

        if (endtime.isBefore(starttime)) {
            errorMessage.add(MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0129),
                    new Object[] { STARTDATE, ENDDATE }));

        }

    } 

    private void checkCorrelationExaminationResult(SearchForm form, List<String> errorMessage) {

        String unexamined = form.getUnexamined();
        String approval = form.getApproval();
        String reject = form.getReject();

        if (unexamined.equals(CommonConstants.OFF) && approval.equals(CommonConstants.OFF) && reject.equals(CommonConstants.OFF)) {
            errorMessage.add(
                    MessageFormat.format(MessageUtil.getMessage(CommonConstants.MS_E0001), new Object[] { RESULT }));
        }
    }

    private List<String> getListNumber(int page, int size, int count) {

        int startNo;
        int endNo = page * size;
        int i;

        List<String> listno = new ArrayList<String>();

        if (page == 1) {
            startNo = page;
        } else {
            startNo = endNo - size + 1;
        }
        if (endNo > count) {
            endNo = count;
        }
        for (i = startNo; i <= endNo; i++) {
            listno.add(CommonUtils.formatPrice(Integer.toString(i)));
        }

        return listno;
    }

    private List<Integer> getListNumberAgain(int page, int size, int amount) {
        int i;
        List<Integer> listno = new ArrayList<Integer>();

        for (i = page * size + 1; i <= ((page * size) + amount); i++) {
            listno.add(i);
        }

        return listno;
    }

}
