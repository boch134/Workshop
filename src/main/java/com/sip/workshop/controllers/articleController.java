package com.sip.workshop.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sip.workshop.entities.Article;
import com.sip.workshop.entities.Provider;
import com.sip.workshop.repository.articleRepository;
import com.sip.workshop.repository.providerRepository;

@Controller
@RequestMapping("/article/")
public class articleController {
	private final articleRepository aR;
	private final providerRepository pR;
	public static String uploadDirectory = System.getProperty("user.dir")+"/src/main/resources/static/uploads";

	@Autowired
	public articleController(articleRepository aR, providerRepository pR) {
		this.aR = aR;
		this.pR = pR;
	}

	@GetMapping("list")
	public String listProviders(Model model) {
		// model.addAttribute("articles", null);

		List<Article> la = (List<Article>)aR.findAll();

		if (la.size() == 0)
			la = null;

		model.addAttribute("articles", la);
		return "article/listArticle";
	}

	@GetMapping("add")
	public String showAddArticleForm(Model model) {

		model.addAttribute("providers", pR.findAll());
		model.addAttribute("article", new Article());
		return "article/addArticle";
	}

	@PostMapping("add")
    public String addArticle(@Valid Article article, BindingResult result, @RequestParam(name = "providerId", required = false) Integer p,
    		@RequestParam("files") MultipartFile[] files,Model model) {
    	model.addAttribute("providers", pR.findAll());
		if (result.hasErrors() || p==null || files.length==0) {
    		return "article/addArticle";
    	}
    	Provider provider = pR.findById(p)
                .orElseThrow(()-> new IllegalArgumentException("Invalid provider Id:" + p));
    	article.setProvider(provider);
    	
    	/// part upload
    	
    	
    	ArrayList<String> listFiles = new ArrayList<String>();
    	for (MultipartFile file:files) {
    	StringBuilder fileName = new StringBuilder();
    	Random ran = new Random();
		int x = ran.nextInt(6) + 5;
		
		fileName.append(x).append(file.getOriginalFilename());
		
    	Path fileNameAndPath = Paths.get(uploadDirectory, fileName.toString());		  try {
			Files.write(fileNameAndPath, file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	listFiles.add(fileName.toString());
    	}
    	
		 article.setPictures(listFiles);
    	 aR.save(article);
    	 return "redirect:list";
	}
    	
    	
    


	@GetMapping("delete/{id}")
	public String deleteProvider(@PathVariable("id") int id, Model model) {
		Article artice = aR.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));
		
		aR.delete(artice);
		return "redirect:../list";
	}

	@GetMapping("update/{id}")
	public String showArticleFormToUpdate(@PathVariable("id") int id, Model model) {
		Article article = aR.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

		model.addAttribute("article", article);
		model.addAttribute("providers", pR.findAll());
		model.addAttribute("idProvider", article.getProvider().getId());

		return "article/updateArticle";
	}

	@PostMapping("update")
	public String updateArticle(@Valid Article article, BindingResult result, Model model,
			@RequestParam(name = "providerId", required = false) int p) {
		if (result.hasErrors()) {
			
			return "article/updateArticle";
		}

		Provider provider = pR.findById(p)
				.orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + p));
		article.setProvider(provider);

		aR.save(article);
		return "redirect:list";
	}
	
	@GetMapping("show/{id}")
    public String showArticleDetails(@PathVariable("id") int id, Model model) {
    	Article article = aR.findById(id)
            .orElseThrow(()->new IllegalArgumentException("Invalid provider Id:" + id));
    	
        model.addAttribute("article", article);
        
        return "article/showArticle";
    }


}
