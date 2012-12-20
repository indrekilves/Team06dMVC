package bg.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class GenericController {

	
    @RequestMapping(value = "/")
	public String showHomepage() {
		return "index";
	}
}
