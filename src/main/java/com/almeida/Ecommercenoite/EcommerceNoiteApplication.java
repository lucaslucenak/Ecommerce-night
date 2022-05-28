package com.almeida.Ecommercenoite;

import com.almeida.Ecommercenoite.enums.UserTypeEnum;
import com.almeida.Ecommercenoite.models.CategoriaModel;
import com.almeida.Ecommercenoite.models.UsuarioModel;
import com.almeida.Ecommercenoite.services.CategoriaService;
import com.almeida.Ecommercenoite.services.UsuarioService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

@SpringBootApplication
public class EcommerceNoiteApplication implements CommandLineRunner {

	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private UsuarioService usuarioService;


	public static void main(String[] args) {
		SpringApplication.run(EcommerceNoiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		String opcaoMenu = "";

		do {
			System.out.println("Ecommerce Izar Store");
			System.out.println("1. Login" +
					"\n2. Cadastro" +
					"\n3. Sair");
			System.out.print("Opcao: ");
			opcaoMenu = sc.nextLine();

			if (Objects.equals(opcaoMenu, "1")) {
				System.out.println("1. Login como Administrador" +
						"\n2. Login como Vendedor" +
						"\n3. Login como Cliente");
				System.out.print("Username: ");
				String username = sc.nextLine();
				System.out.print("Password: ");
				String password = sc.nextLine();
				Boolean isLoggedIn = false;

				System.out.print("Opcao: ");
				opcaoMenu = sc.nextLine();

				if (Objects.equals(opcaoMenu, "1")) {
					isLoggedIn = login(UserTypeEnum.Administrador, username, password);

					if (isLoggedIn) {
						System.out.println("LOGOU LOGOU");
					}
					else {
						System.out.println("Nao logou");
					}
				}
				else if (Objects.equals(opcaoMenu, "2")) {
					isLoggedIn = login(UserTypeEnum.Vendedor, username, password);
				}
			}
			else if (Objects.equals(opcaoMenu, "2")) {
				System.out.println("1. Cadastro de Vendedor" +
						"\n3. Cadastro de Cliente");

			}
			else {
				System.out.println("Opcao Invalida.");
			}

		} while (opcaoMenu.equals("3"));

		if (Objects.equals(opcaoMenu, "3")) {
			System.out.println("Volte sempre!");
			System.exit(0);
		}

//		categoriaService.createCategoria(new CategoriaModel("Games"));
//		List<CategoriaModel> categorias = categoriaService.findCategoriaByNome("games");
//
//		for (CategoriaModel categoriaModel: categorias) {
//			System.out.println(categoriaModel.getNome());
//		}
	}

	public Boolean login(UserTypeEnum userType, String username, String password) {
		if (userType == UserTypeEnum.Administrador) {
			return usuarioService.login(new UsuarioModel(UserTypeEnum.Administrador, username, password));
		}
		else if (userType == UserTypeEnum.Cliente) {
			return usuarioService.login(new UsuarioModel(UserTypeEnum.Cliente, username, password));
		}
		else if (userType == UserTypeEnum.Vendedor) {
			return usuarioService.login(new UsuarioModel(UserTypeEnum.Vendedor, username, password));
		}
		else {
			return false;
		}
	}


}
