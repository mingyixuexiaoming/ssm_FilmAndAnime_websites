package com.sheep.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.sheep.pojo.Admin;
import com.sheep.pojo.Movie;
import com.sheep.pojo.User;
import com.sheep.service.AdminService;
import com.sheep.service.MovieService;
import com.sheep.service.UserService;
import com.sheep.utils.SaveFile;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private MovieService movieService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * ����̨�Ƿ��¼
	 * @param admin
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/checkAdmin")
	public String checkAdmin(HttpSession session) {
		Admin admin = (Admin) session.getAttribute("admin");
		if(null!=admin) {
			return "redirect:jsp/admin/index.jsp";
		}else {
			return "redirect:jsp/admin/adminLogin.jsp";
		}
	}
	
	/**
	 * ��̨��¼
	 * @param admin
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/adminLogin")
	public String adminLogin(Admin admin,HttpSession session,HttpServletRequest request) {
		Admin adminResult = this.adminService.adminLogin(admin);
		if (null != adminResult) {
			session.setAttribute("admin", adminResult);
			return "redirect:jsp/admin/index.jsp";
		} else {
			request.setAttribute("mess", "��֤ʧ�ܣ���ע���ɫ���û����������Ƿ�ƥ�䣡");
			return "jsp/admin/error.jsp";
		}
	}
	
	/**
	 * ��̨�˳���¼
	 */
	@RequestMapping("/adminLogout")
	public String adminLogout(HttpSession session) {
		session.getAttribute("admin");
		session.removeAttribute("admin");
		return "redirect:jsp/admin/adminLogin.jsp";
	}

	
	/**
	 * ��ѯ�û��б�
	 * @param admin
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/userList")
	public String userList(Admin admin,HttpSession session,HttpServletRequest request) {
		return "jsp/admin/userList.jsp";
	}
	
	/**
	 * ��ѯ��Ƶ�б�
	 * @param admin
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping("/movieList")
	public String movieList(Admin admin,Movie movie,HttpSession session,HttpServletRequest request) {
		String operation = request.getParameter("operation");
		if(operation.equals("silder")) {//�ֲ�
			List<Movie> listMV = this.adminService.getMovieBySlider();
			request.setAttribute("listMV", listMV);
			request.setAttribute("operation", "silder");
		}else {
			if(operation.equals("movie")) {//��Ӱ
				movie.setCategoryId(1l);
				request.setAttribute("operation", "movie");
			}else if(operation.equals("TVplay")){//���Ӿ�
				movie.setCategoryId(2l);
				request.setAttribute("operation", "TVplay");
			}else if(operation.equals("comic")){//����
				movie.setCategoryId(3l);
				request.setAttribute("operation", "comic");
			}else {//����
				movie.setCategoryId(4l);
				request.setAttribute("operation", "variety");
			}
			
			List<Movie> listMV = this.movieService.getMoviesByUserID(movie);
			request.setAttribute("listMV", listMV);
		}
		return "jsp/admin/movieList.jsp";
	}
	
	/**
	 * ���ֲ���Ƶ�¼�
	 * @param admin
	 * @param request
	 * @return
	 */
	@RequestMapping("/changeMovie")
	public String changeMovie(Admin admin,HttpServletRequest request) {
		Movie movie = new Movie();
		movie.setId(request.getParameter("movieid"));
		movie.setSliderImage("");
		this.movieService.updateByCondiction(movie);
		return "redirect:movieList?operation=slider";
	}
	
	/**
	 * �������в����ֲ�����Ƶ
	 * @param movie
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMovieNoSilder")
	public String getMovieNoSilder(Movie movie,HttpServletRequest request) {
		List<Movie> lmv = this.movieService.getMovieNoSilder(movie);
		request.setAttribute("lmv", lmv);
		return "jsp/admin/addSilderList.jsp";
	}
	
	/**
	 * ȥuploadSilderImage.jspҳ
	 */
	@RequestMapping("/gotoUploadSilderImage")
	public String gotoUploadSilderImage(HttpServletRequest request) {
		String movieId = request.getParameter("movieId");
		request.setAttribute("movieId", movieId);
		return "jsp/admin/uploadSilderImage.jsp";
	}
	/**
	 * �ϴ���ҳ�ֲ�ͼƬ
	 */
	@RequestMapping("/uploadSilderImage")
	public String uploadSilderImage(Movie movie,HttpSession session,
			@RequestParam(value = "silderPrices") MultipartFile silderPrices,
			HttpServletRequest request) {
		Admin admin = (Admin) session.getAttribute("admin");
		if(!silderPrices.isEmpty()) {
			SaveFile.saveSilderPicture(admin, silderPrices, movie);
		}
		movie.setSliderImage(".jpg");
		this.movieService.updateByCondiction(movie);
		return "checkAdmin";
	}
	
	/**
	 * ��ȡ�û��б�
	 */
	@RequestMapping("/getUserList")
	public String getUserList(HttpServletRequest request) {
		List<User> userList = this.userService.getUserList();
		request.setAttribute("userList", userList);
		return "jsp/admin/userList.jsp";
	}
	
	/**
	 * ��ȡ�û���Ƶ�б�
	 */
	@RequestMapping("/getUserMovies")
	public String getUserMovies(Movie movie,HttpServletRequest request) {
		List<Movie> movies = this.movieService.getUserMovies(movie);
		request.setAttribute("movies", movies);
		return "jsp/admin/userMovieList.jsp";
	}
	
	/**
	 * ��ȡ�˻��б�
	 */
	@RequestMapping("/getAdminList")
	public String getAdminList(HttpServletRequest request) {
		List<Admin> adminList = this.adminService.getAdminList();
		request.setAttribute("adminList", adminList);
		return "jsp/admin/adminList.jsp";
	}
	
	
	/**
	 * �����ʻ�
	 */
	@RequestMapping("/addAdmin")
	public String addAdmin(Admin admin,HttpServletRequest request) {
		this.adminService.addAdmin(admin);
		return "redirect:checkAdmin";
	}
	
	/**
	 * ��ȡ�˻��б�
	 */
	@RequestMapping("/authorization")
	public String authorization(HttpServletRequest request) {
		List<Admin> adminList = this.adminService.getAdminList();
		request.setAttribute("adminList", adminList);
		return "jsp/admin/authorization.jsp";
	}
	
	
	/**
	 * ��ȡ�˻���Ϣ
	 */
	@RequestMapping("/changeAuth")
	public String changeAuth(Admin admin,HttpServletRequest request) {
		Admin result = this.adminService.getAdminById(admin);
		request.setAttribute("result", result);
		return "jsp/admin/changeAuth.jsp";
	}
	
	/**
	 * �޸��˻�Ȩ��
	 */
	@RequestMapping("/updateAuth")
	public String updateAuth(Admin admin,HttpServletRequest request) {
		this.adminService.updateAuth(admin);
		return "redirect:checkAdmin";
	}
	
	
	
}
