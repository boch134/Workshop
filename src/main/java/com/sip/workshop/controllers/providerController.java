package com.sip.workshop.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sip.workshop.entities.Article;
import com.sip.workshop.entities.Provider;
import com.sip.workshop.repository.providerRepository;

@Controller
@RequestMapping("/provider")
public class providerController {
	
	providerRepository pR;
	
	@Autowired
	public providerController(providerRepository pR) {
		super();
		this.pR = pR;
	}
	
	@RequestMapping("/list")
	public String listProvider(Model model) {
		List<Provider> lp = (List<Provider>)pR.findAll(); 
		if (lp.isEmpty())
			lp=null;
		model.addAttribute("providers", lp);
		
		return "provider/listProvider";
	}
	
	@GetMapping("/add")
	public String addProviderForm(Model model) {
		Provider provider = new Provider();
		model.addAttribute("provider", provider);
		return "provider/addProvider";
		
	}
	
	@PostMapping("/add")
	public String addProvider(@Valid Provider provider,BindingResult result,Model model) {
		if (result.hasErrors()) {
			return "provider/addProvider";
			}
			pR.save(provider);
			return "redirect:list";
	}
	
	@GetMapping("/delete/{id}")
	public String deleteProvider(@PathVariable("id") int id) {
		Provider provider = pR.findById(id)

				.orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));


				pR.delete(provider);

				
				return "redirect:../list";
	}
	
	@GetMapping("/update/{id}")
	public String showProviderFormToUpdate(@PathVariable("id") int id, Model model) {
		Provider provider = pR.findById(id)

				.orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));

				

				model.addAttribute("provider", provider);

				return "provider/updateProvider";
	}

	@PostMapping("/update")
	public String updateProvider(@Valid Provider provider,BindingResult result) {

		if (result.hasErrors()) {
			return "provider/updateProvider";
			}

			pR.save(provider);
			return "redirect:list";

	}
	
	@GetMapping("show/{id}")
	public String showProvider(@PathVariable("id") int id, Model model) {
		Provider provider = pR.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid provider Id:" + id));
		List<Article> articles = pR.findArticlesByProvider(id);
		for (Article a : articles)
			System.out.println("Article = " + a.getLabel());
		
		model.addAttribute("articles", articles);
		model.addAttribute("provider", provider);
		return "provider/showProvider";
	}


	
	
	
	

}
