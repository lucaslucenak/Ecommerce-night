package com.almeida.Ecommercenoite;

import com.almeida.Ecommercenoite.entities.CarrinhoDeCompras;
import com.almeida.Ecommercenoite.enums.TipoPagamentoEnum;
import com.almeida.Ecommercenoite.enums.UserTypeEnum;
import com.almeida.Ecommercenoite.exceptions.ProdutoAlreadyExistsException;
import com.almeida.Ecommercenoite.exceptions.UsernameTakenException;
import com.almeida.Ecommercenoite.models.CategoriaModel;
import com.almeida.Ecommercenoite.models.ProdutoModel;
import com.almeida.Ecommercenoite.models.UsuarioModel;
import com.almeida.Ecommercenoite.models.VendaModel;
import com.almeida.Ecommercenoite.repositories.ProdutoCustomRepository;
import com.almeida.Ecommercenoite.services.CategoriaService;
import com.almeida.Ecommercenoite.services.ProdutoService;
import com.almeida.Ecommercenoite.services.UsuarioService;
import com.almeida.Ecommercenoite.services.VendaService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

@SpringBootApplication
public class EcommerceNoiteApplication implements CommandLineRunner {

	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ProdutoCustomRepository produtoCustomRepository;
	@Autowired
	private VendaService vendaService;


	public static void main(String[] args) {
		SpringApplication.run(EcommerceNoiteApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner sc = new Scanner(System.in);
		Scanner sc1 = new Scanner(System.in);
		Scanner sc2 = new Scanner(System.in);
		Scanner sc3 = new Scanner(System.in);

		int opcaoMenu;

		do {
			System.out.println("Ecommerce Izar Store");
			System.out.println("1. Login" +
					"\n2. Cadastro" +
					"\n0. Sair");
			System.out.print("Opcao: ");
			opcaoMenu = sc.nextInt();

//--------------------------------------------------------LOGIN---------------------------------------------------------
			if (opcaoMenu == 1) {
				int opcaoLogin;
				System.out.println("1. Login como Administrador" +
						"\n2. Login como Vendedor" +
						"\n3. Login como Cliente");
				String username = "";
				String password = "";
				Boolean isLoggedIn = false;

				System.out.print("Opcao: ");
				opcaoLogin = sc.nextInt();

//-------------------------------------------------LOGIN ADMINISTRADOR--------------------------------------------------
				if (opcaoLogin == 1) { // OK
					clearBuffer(sc);
					System.out.print("Username: ");
					username = sc.nextLine();
					clearBuffer(sc);
					System.out.print("Password: ");
					password = sc1.nextLine();
					isLoggedIn = login(UserTypeEnum.Administrador, username, password);

					if (isLoggedIn) {
						int opcaoMenuAdm = 0;
						do {
							System.out.println("1. Criar nova categoria" +
									"\n2. Pesquisar categoria" +
									"\n3. Atualizar categoria" +
									"\n4. Excluir categoria" +
									"\n5. Logout");
							clearBuffer(sc);
							opcaoMenuAdm = sc.nextInt();

							if (opcaoMenuAdm == 1) { // OK
								System.out.print("Nome da nova categoria: ");
								String nomeCadastro = sc2.nextLine();
								try {
									categoriaService.createCategoria(new CategoriaModel(nomeCadastro.toUpperCase(Locale.ROOT)));

								} catch (UsernameTakenException e) {
									System.out.println("Categoria já cadastrada");
								}
							}
							else if (opcaoMenuAdm == 2) { // OK
								List<CategoriaModel> categorias = categoriaService.getAllCategorias();
								System.out.println("Categorias cadastradas:");
								for (CategoriaModel i : categorias) {
									System.out.println(i.getId() + ". " + i.getNome());
								}
							}
							else if (opcaoMenuAdm == 3) { // OK
								List<CategoriaModel> categoriasUpdate = categoriaService.getAllCategorias();
								System.out.println("Categorias cadastradas:");
								for (CategoriaModel i : categoriasUpdate) {
									System.out.println(i.getId() + ". " + i.getNome());
								}
								System.out.print("Id:");
								int idAtualizacao = sc.nextInt();
								for (CategoriaModel i : categoriasUpdate) {
									if (i.getId() == idAtualizacao) {
										System.out.print("Nome para atualizar: ");
										String nomeAtualizacao = sc1.nextLine().toUpperCase(Locale.ROOT);
										i.setNome(nomeAtualizacao);
										categoriaService.updateCategoria(i);
									}
								}
							}
							else if (opcaoMenuAdm == 4) { // OK
								System.out.println("Quer mesmo deletar as categorias? (Isto excluirá todos os produtos dessa categoria)");
								System.out.println("1. Sim" +
										"\n2. Não");
								System.out.print("Opcao: ");
								int opcaoMenu4 =  sc1.nextInt();
								if (opcaoMenu4 == 1) {
									List<CategoriaModel> categoriasDelete = categoriaService.getAllCategorias();
									System.out.print("Categorias cadastradas:");
									for (CategoriaModel i : categoriasDelete) {
										System.out.println(i.getId() + ". " + i.getNome());
									}
									System.out.print("Id da categoria para deletar: ");
									int idCategoriaDelete = sc.nextInt();

									for (CategoriaModel i : categoriasDelete) {
										if (i.getId() == idCategoriaDelete) {
											List<ProdutoModel> produtosDeleteByCategoria = produtoService.getAllProdutos();
											for (ProdutoModel j : produtosDeleteByCategoria) {
												if (j.getIdCategoria() == idCategoriaDelete) {
													produtoService.deleteProdutoById(j.getId());
												}
											}
											categoriaService.deleteCategoriaById(i.getId());
										}
									}
								}
								else {
									System.out.println("Opcao inválida");
								}
							}
							else {
								System.out.println("Opcao invalida.");
							}
						} while (opcaoMenuAdm != 5);

					}
					else {
						System.out.println("Login falhou.");
					}
				}

//----------------------------------------------------LOGIN VENDEDOR----------------------------------------------------
				else if (opcaoLogin == 2) {
					System.out.print("Username: ");
					username = sc3.nextLine();
					System.out.print("Password: ");
					password = sc3.nextLine();
					isLoggedIn = login(UserTypeEnum.Vendedor, username, password);

					if (isLoggedIn) {
						System.out.println("LOGOU LOGOU vendedor");
						int opcaoMenuVendedor = 0;
						do {
							System.out.println("1. Criar novo produto" +
									"\n2. Pesquisar produto" +
									"\n3. Atualizar produto" +
									"\n4. Excluir produto" +
									"\n5. Logout");
							System.out.print("Opcao: ");
							opcaoMenuVendedor = sc.nextInt();

							if (opcaoMenuVendedor == 1) { // OK
								System.out.println("Categorias:");
								List<CategoriaModel> categorias = categoriaService.getAllCategorias();
								for (CategoriaModel i : categorias) {
									System.out.println(i.getId() + ". " + i.getNome());
								}
								System.out.print("Escolha o id da categoria: ");
								int idCategoria = sc1.nextInt();

								for (CategoriaModel i : categorias) {
									if (i.getId() == idCategoria) {
										System.out.print("Nome do produto: ");
										String nomeProduto = sc2.nextLine();
										System.out.print("Preco do produto: ");
										Double precoProduto = sc2.nextDouble();
										try {
											produtoService.createProduto(new ProdutoModel(nomeProduto.toUpperCase(Locale.ROOT), precoProduto, i.getId()));
										} catch (ProdutoAlreadyExistsException e) {
											System.out.println("Produto ja cadastrado!");
										}
									}
								}
							}
							else if (opcaoMenuVendedor == 2) { // OK
								List<ProdutoModel> allProdutos = produtoService.getAllProdutos();
								for (ProdutoModel i : allProdutos) {
									System.out.println(i.getId() + ". " + i.getNome());
								}
							}
							else if (opcaoMenuVendedor == 3) { // OK
								List<ProdutoModel> produtosUpdate = produtoService.getAllProdutos();

								for (ProdutoModel i : produtosUpdate) {
									System.out.println(i.getId() + ". " + i.getNome());
								}
								System.out.println("Id do produto para atualizacao: ");
								int idAtualizacao = sc1.nextInt();

								for (ProdutoModel i : produtosUpdate) {
									if (i.getId() == idAtualizacao) {
										System.out.print("Novo nome: ");
										String novoNome = sc2.nextLine();
										System.out.print("Novo preco: ");
										Double novoPreco = sc2.nextDouble();
										i.setNome(novoNome);
										i.setPreco(novoPreco);
										produtoService.updateProduto(i);
									}
								}
							}
							else if (opcaoMenuVendedor == 4) { // OK
								List<ProdutoModel> produtosDelete = produtoService.getAllProdutos();

								for (ProdutoModel i : produtosDelete) {
									System.out.println(i.getId() + ". " + i.getNome());
								}
								System.out.print("Id do produto para deletar: ");
								Long idProdutoDelete = sc1.nextLong();
								for (ProdutoModel i : produtosDelete) {
									if (i.getId() == idProdutoDelete) {
										System.out.println("Quer mesmo deletar o produto?");
										System.out.println("1. Sim" +
												"\n2. Não");
										System.out.print("Opcao: ");
										int confirmarDelete = sc1.nextInt();
										if (confirmarDelete == 1) {
											produtoService.deleteProdutoById(idProdutoDelete);
										}
										else if (confirmarDelete == 2) {
											System.out.println("Cancelado.");
										}
										else {
											System.out.println("Opcao invalida");
										}
									}
									else {
										System.out.println("Produto nao encontrado");
									}
								}
							}
							else {
								System.out.println("opcao invalida.");
							}
						} while(opcaoMenuVendedor != 5);
					}
					else {
						System.out.println("Nao logou");
					}
				}

//----------------------------------------------------LOGIN CLIENTE-----------------------------------------------------
				else if (opcaoLogin == 3) {
					System.out.print("Username: ");
					username = sc3.nextLine();
					System.out.print("Password: ");
					password = sc3.nextLine();
					isLoggedIn = login(UserTypeEnum.Cliente, username, password);

					if (isLoggedIn) {
						System.out.println("LOGOU LOGOU cliente");
						int opcaoMenuCliente = 0;
						CarrinhoDeCompras carrinhoDeCompras = new CarrinhoDeCompras();
						do {
							System.out.println("1. Navegar no Ecommerce" +
									"\n2. Logout");
							System.out.print("Opcao: ");
							opcaoMenuCliente = sc2.nextInt();
							if (opcaoMenuCliente == 1) {
								int opcaoCompras = 0;
								do {
									System.out.println("1. Adicionar produto" +
											"\n2. Visualizar carrinho" +
											"\n3. Encerrar compras");
									System.out.print("Opcao: ");
									opcaoCompras = sc2.nextInt();
									if (opcaoCompras == 1) {
										List<ProdutoModel> produtos = produtoService.getAllProdutos();
										for (ProdutoModel i : produtos) {
											System.out.println("Produtos: ");
											System.out.println(i.getId() + ". " + i.getNome() + " R$" + i.getPreco());
											System.out.println("Deseja adicionar algum produto? ");
											System.out.println("1. Sim" +
													"\n2. Não");
											int adicionarProduto = sc2.nextInt();
											if (adicionarProduto == 1 ) {
												System.out.print("Id do produto: ");
												Long idProdutoAdicionar = sc2.nextLong();
												for (ProdutoModel j : produtos) {
													if (j.getId() == idProdutoAdicionar) {
														carrinhoDeCompras.addProduto(j);
													}
												}
											}
											else if (adicionarProduto == 2) {

											}
											else {
												System.out.println("Opcao invalida");
											}
										}
									}
									else if (opcaoCompras == 2) {
										for (ProdutoModel i : carrinhoDeCompras.getProdutos()) {
											System.out.println(i.getNome() + " R$" + i.getPreco());
										}
									}
									else if (opcaoCompras == 3) {
										System.out.println("Metodos de pagamento: ");
										System.out.println("1. Boleto" +
												"\n2. Débito" +
												"\n3. Crédito" +
												"\n4. Pix");
										System.out.print("Metodo de pagamento:");
										int metodoPagamento = sc3.nextInt();
										System.out.println("Endereco de envio: ");
										String enderecoEnvio = sc.nextLine();
										for (ProdutoModel i : carrinhoDeCompras.getProdutos()) {
											if (metodoPagamento == 1) {
												vendaService.createVenda(new VendaModel(
														usuarioService.findUsuarioByNome(username).get(0).getId(), i.getId(), TipoPagamentoEnum.BOLETO, enderecoEnvio
												));
											}
											else if (metodoPagamento == 2) {

											}
											else if (metodoPagamento == 3) {

											}
											else if (metodoPagamento == 4) {

											}
											else {
												System.out.println("Opcao invalida.");
											}
										}
										System.out.println("Compas finalizadas, voltando ao menu...");
									}
									else {
										System.out.println("Opcao invalida.");
									}
								} while (opcaoCompras != 3);

							}
							else if (opcaoMenuCliente == 2) {
								System.out.println("Logout.");
							}
							else if (opcaoMenuCliente == 3) {

							}
							else {
								System.out.println("Opcao invalida.");
							}
						} while (opcaoMenuCliente != 2);
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
			else if (opcaoMenu == 2) {
				System.out.println("1. Cadastro de Vendedor" +
						"\n2. Cadastro de Cliente");
				String username = "";
				String password = "";
				System.out.print("Opcao: ");
				int opcaoMenuCadastro = sc.nextInt();

				//CADASTRO DE VENDEDOR
				if (opcaoMenuCadastro == 1) {
					System.out.print("Username para cadastro: ");
					username = sc1.nextLine();
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
				else if (opcaoMenuCadastro == 2) {
					System.out.print("Username para cadastro: ");
					username = sc1.nextLine();
					System.out.print("Password para cadastro: ");
					password = sc2.nextLine();
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
			else if (opcaoMenu == 0) {
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

	private static void clearBuffer(Scanner scanner) {
		if (scanner.hasNextLine()) {
			scanner.nextLine();
		}
	}


}
