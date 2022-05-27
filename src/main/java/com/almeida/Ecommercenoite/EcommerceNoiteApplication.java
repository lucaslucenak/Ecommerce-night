package com.almeida.Ecommercenoite;

import com.almeida.Ecommercenoite.models.CategoriaModel;
import com.almeida.Ecommercenoite.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class EcommerceNoiteApplication implements CommandLineRunner {

	@Autowired
	private CategoriaService categoriaService;

	public static void main(String[] args) {
		SpringApplication.run(EcommerceNoiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		categoriaService.createCategoria(new CategoriaModel("Games"));
		List<CategoriaModel> categorias = categoriaService.findCategoriaByNome("games");

		for (CategoriaModel categoriaModel: categorias) {
			System.out.println(categoriaModel.getNome());
		}
	}

}
