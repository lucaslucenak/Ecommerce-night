package com.almeida.Ecommercenoite;

import com.almeida.Ecommercenoite.enums.UserTypeEnum;
import com.almeida.Ecommercenoite.exceptions.UsernameTakenException;
import com.almeida.Ecommercenoite.models.CategoriaModel;
import com.almeida.Ecommercenoite.models.ProdutoModel;
import com.almeida.Ecommercenoite.models.UsuarioModel;
import com.almeida.Ecommercenoite.services.CategoriaService;
import com.almeida.Ecommercenoite.services.ProdutoService;
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
	@Autowired
	private ProdutoService produtoService;


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
					"\n0. Sair");
			System.out.print("Opcao: ");
			opcaoMenu = sc.nextLine();

			//LOGIN
			if (Objects.equals(opcaoMenu, "1")) {
				System.out.println("1. Login como Administrador" +
						"\n2. Login como Vendedor" +
						"\n3. Login como Cliente");
				String username = "";
				String password = "";
				Boolean isLoggedIn = false;

				System.out.print("Opcao: ");
				opcaoMenu = sc.nextLine();

				if (Objects.equals(opcaoMenu, "1")) {
					System.out.print("Username: ");
					username = sc.nextLine();
					System.out.print("Password: ");
					password = sc.nextLine();
					isLoggedIn = login(UserTypeEnum.Administrador, username, password);

					if (isLoggedIn) {
						do {
							System.out.println("LOGOU LOGOU admin");
							System.out.println("1. Criar nova categoria" +
									"\n2. Pesquisar categoria" +
									"\n3. Atualizar categoria" +
									"\n4. Excluir categoria (Excluirá todos os produtos referentes)" +
									"\n5. Logout");
							opcaoMenu = sc.nextLine();
							switch (opcaoMenu) {
								case "1": // OK
									System.out.print("Nome da categoria:");
									String nomeCadastro = sc.nextLine();
									try {
										categoriaService.createCategoria(new CategoriaModel(nomeCadastro));

									} catch (UsernameTakenException e) {
										System.out.println("Categoria já cadastrada");
									}
									break;
								case "2": // OK
									List<CategoriaModel> categorias = categoriaService.getAllCategorias();
									System.out.print("Categorias cadastradas:");
									for (CategoriaModel i : categorias) {
										System.out.println(i.getNome());
									}
									break;
								case "3": // OK
									System.out.print("Nome da categoria para atualizacao:");
									String nomeAtualizacao = sc.nextLine();
									boolean hasCategoria = false;
									try {
										categoriaService.findCategoriaByNome(nomeAtualizacao);
										hasCategoria = true;
									} catch (Exception e) {
										System.out.println("Erro ao pesquisar categoria");
										hasCategoria = false;
									}
									if (hasCategoria) {
										System.out.print("Novo nome: ");
										String novoNome = sc.nextLine();
										categoriaService.createCategoria(new CategoriaModel(novoNome));
									}
									break;
								case "4": // OK
									System.out.print("Quer mesmo deletar as categorias? (Isto excluirá todos os produtos dessa categoria.");
									System.out.print("1. Sim" +
											"\n2. Não");
									System.out.print("Opcao: ");
									opcaoMenu = sc.nextLine();
									if (opcaoMenu.equals("1")) {
										System.out.print("Categoria para deletar: ");
										String nomeCategoriaDelete = sc.nextLine();
										List<CategoriaModel> categoriaDelete = categoriaService.findCategoriaByNome(nomeCategoriaDelete);
										if (categoriaDelete.size() > 0) {
											for (CategoriaModel i : categoriaDelete) {
												categoriaService.deleteCategoriaById(i.getId());
											}
										}
									}
									else if (opcaoMenu.equals("2")) {
									}
									else {
										System.out.println("Opcao inválida");
									}
									break;
								default:
									System.out.println("Opcao invalida.");
									break;
							}
						} while (!opcaoMenu.equals("5"));
					}
					else {
						System.out.println("Nao logou");
					}
				}
				else if (Objects.equals(opcaoMenu, "2")) {
					System.out.print("Username: ");
					username = sc.nextLine();
					System.out.print("Password: ");
					password = sc.nextLine();
					isLoggedIn = login(UserTypeEnum.Vendedor, username, password);

					if (isLoggedIn) {
						System.out.println("LOGOU LOGOU vendedor");
					}
					else {
						System.out.println("Nao logou");
					}
				}
				else if (Objects.equals(opcaoMenu, "3")) {
					System.out.print("Username: ");
					username = sc.nextLine();
					System.out.print("Password: ");
					password = sc.nextLine();
					isLoggedIn = login(UserTypeEnum.Cliente, username, password);

					if (isLoggedIn) {
						System.out.println("LOGOU LOGOU cliente");
					}
					else {
						System.out.println("Nao logou");
					}
				}
				else {
					System.out.println("Opcao invalida.");
				}
			}

//-------------------------------------------------------CADASTRO-------------------------------------------------------
			else if (Objects.equals(opcaoMenu, "2")) {
				System.out.println("1. Cadastro de Vendedor" +
						"\n2. Cadastro de Cliente");
				String username = "";
				String password = "";
				opcaoMenu = sc.nextLine();

				//CADASTRO DE VENDEDOR
				if (Objects.equals(opcaoMenu, "1")) {
					System.out.print("Username para cadastro: ");
					username = sc.nextLine();
					System.out.print("Password para cadastro: ");
					password = sc.nextLine();
					try {
						usuarioService.createUsuario(new UsuarioModel(UserTypeEnum.Vendedor, username, password));
						System.out.println("Usuario vendedor cadastrado com sucesso.");
 					} catch (UsernameTakenException e) {
						System.out.println("Username já utilizado");
					}
				}

				//CADASTRO DE CLIENTE
				else if (Objects.equals(opcaoMenu, "2")) {
					System.out.print("Username para cadastro: ");
					username = sc.nextLine();
					System.out.print("Password para cadastro: ");
					password = sc.nextLine();
					try {
						usuarioService.createUsuario(new UsuarioModel(UserTypeEnum.Cliente, username, password));
						System.out.println("Usuario cliente cadastrado com sucesso.");
					} catch (UsernameTakenException e) {
						System.out.println("Username já utilizado");
					}
				}
				else {
					System.out.println("Opcao invalida.");
				}

			}

//------------------------------------------------------DISCONNECT------------------------------------------------------
			else if (Objects.equals(opcaoMenu, "0")) {
				System.out.println("Volte sempre!");
				System.exit(0);
				break;
			}
			else {
				System.out.println("Opcao Invalida.");
			}

		} while (true);

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
