package com.almeida.Ecommercenoite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EcommerceNoiteApplication {

//	@Autowired
//	private CategoriaService categoriaService;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceNoiteApplication.class, args);
//		CategoriaService categoriaService = new CategoriaService();

//		categoriaService.createCategoria(new CategoriaModel("Games"));
	}

}
