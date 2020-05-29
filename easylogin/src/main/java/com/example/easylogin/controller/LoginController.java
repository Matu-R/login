package com.example.easylogin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.easylogin.model.dao.UserRepository;
import com.example.easylogin.model.entity.User;

@Controller
public class LoginController {

	@Autowired
	UserRepository userRepos;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(
			@RequestParam("userName") String userName,
			@RequestParam("password") String password,
			Model m) {
		
		String message = "Welcome!　";
		
		List<User> users = userRepos.findByUserNameAndPassword(userName, password);
		if (users.size() > 0) {
			User user = users.get(0);
			message += user.getFullName();
		} else {
			message += "guest";
		}
		
		m.addAttribute("message", message);
		
		return "login";
	}

	@ResponseBody
	public String showUsers() {
		/*
		 * UserRepositoryのインスタンスからfindAllメソッドを呼び出し、Userエンティティのリストを取得しています。
		 * テキスト通り作成している場合、DBにはレコードが1件しか存在していませんが、List型で取得します。
		 * なせなら、findAllメソッドの戻り値のデータ型がListだからです。
		 */
		List<User> users = userRepos.findAll();
		
		/*
		 * 上記で取得したリストの〇番目を取得しています。
		 */
		
		User user = users.get(0);
		
		/*
		 * Userエンティティのインスタンスから、userNameとpasswordを連結した文字列を作成しています。
		 */
		String info = user.getUserName() + " " + user.getPassword();
		
		return info;
	}
	
	
}

