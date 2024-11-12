package com.catalog.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catalog.model.CatalogService;
import com.catalog.model.CatalogVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;

public class CatalogServlet extends HttpServlet{
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入類別編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/member/select.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer id = null;
				try {
					id = Integer.valueOf(str);
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/member/select.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				CatalogService catalogSvc = new CatalogService();
				CatalogVO catalogVO = catalogSvc.getOneCatalog(id);
				if (catalogVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/member/select.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("catalogVO", catalogVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/member/listOneCatalog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
				/***************************1.接收請求參數****************************************/
				Integer id = Integer.valueOf(req.getParameter("id"));
				
				/***************************2.開始查詢資料****************************************/
				CatalogService catalogSvc = new CatalogService();
				CatalogVO catalogVO = catalogSvc.getOneCatalog(id);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("catalogVO", catalogVO);         // 資料庫取出的catalogVO物件,存入req
				String url = "/back-end/member/update_catalog_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer id = Integer.valueOf(req.getParameter("id").trim());
				
				String name = req.getParameter("name");
				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (name == null || name.trim().length() == 0) {
					errorMsgs.add("類別名稱：請勿空白");
				} else if(!name.trim().matches(enameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("類別名稱：只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
					


				CatalogVO catalogVO = new CatalogVO();
				catalogVO.setId(id);
				catalogVO.setName(name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
req.setAttribute("catalogVO", catalogVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/member/update_catalog_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				CatalogService catalogSvc = new CatalogService();
				catalogVO = catalogSvc.updateCatalog(id, name);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("catalogVO", catalogVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/member/listOneCatalog.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String name = req.getParameter("name");
				String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (name == null || name.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if(!name.trim().matches(nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
	
				
				CatalogVO catalogVO = new CatalogVO();
				catalogVO.setName(name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
req.setAttribute("catalogVO", catalogVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/member/addCatalog.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				CatalogService catalogSvc = new CatalogService();
				catalogVO = catalogSvc.addCatalog(name);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/member/listAll.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
				/***************************1.接收請求參數***************************************/
				Integer id = Integer.valueOf(req.getParameter("id"));
				
				/***************************2.開始刪除資料***************************************/
				CatalogService catalogSvc = new CatalogService();
				catalogSvc.deleteCatalog(id);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/member/listAll.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
		}
	}
}
