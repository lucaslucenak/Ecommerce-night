package com.almeida.Ecommercenoite;

import com.almeida.Ecommercenoite.entities.CarrinhoDeCompras;
import com.almeida.Ecommercenoite.entities.TotalVendasCategoria;
import com.almeida.Ecommercenoite.entities.TotalVendasProdutos;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

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

		int opcaoMenu;

		do {
			System.out.println("Ecommerce Izar Store");
			System.out.println("1. Login" +
					"\n2. Cadastro" +
					"\n0. Sair");
			System.out.print("Opcao: ");
			opcaoMenu = Integer.parseInt(sc.nextLine());

//--------------------------------------------------------LOGIN---------------------------------------------------------
			if (opcaoMenu == 1) {
				int opcaoLogin;
				System.out.println("1. Login como Administrador" +
						"\n2. Login como Vendedor" +
						"\n3. Login como Cliente");
				String username;
				String password;
				Boolean isLoggedIn;

				System.out.print("Opcao: ");
				opcaoLogin = Integer.parseInt(sc.nextLine());

//-------------------------------------------------LOGIN ADMINISTRADOR--------------------------------------------------
				if (opcaoLogin == 1) { // OK
					System.out.print("Username: ");
					username = sc.nextLine();
					System.out.print("Password: ");
					password = sc.nextLine();
					isLoggedIn = login(UserTypeEnum.Administrador, username, password);

					if (isLoggedIn) {
						int opcaoMenuAdm = 0;
						do {
							System.out.println("1. Criar nova categoria" +
									"\n2. Pesquisar categoria" +
									"\n3. Atualizar categoria" +
									"\n4. Excluir categoria" +
									"\n5. Logout");
							opcaoMenuAdm = Integer.parseInt(sc.nextLine());

							if (opcaoMenuAdm == 1) { // OK
								System.out.print("Nome da nova categoria: ");
								String nomeCadastro = sc.nextLine();
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
								int idAtualizacao = Integer.parseInt(sc.nextLine());
								for (CategoriaModel i : categoriasUpdate) {
									if (i.getId() == idAtualizacao) {
										System.out.print("Nome para atualizar: ");
										String nomeAtualizacao = sc.nextLine().toUpperCase(Locale.ROOT);
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
								int opcaoMenu4 =  Integer.parseInt(sc.nextLine());
								if (opcaoMenu4 == 1) {
									List<CategoriaModel> categoriasDelete = categoriaService.getAllCategorias();
									System.out.print("Categorias cadastradas:");
									for (CategoriaModel i : categoriasDelete) {
										System.out.println(i.getId() + ". " + i.getNome());
									}
									System.out.print("Id da categoria para deletar: ");
									int idCategoriaDelete = Integer.parseInt(sc.nextLine());

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
					username = sc.nextLine();
					System.out.print("Password: ");
					password = sc.nextLine();
					isLoggedIn = login(UserTypeEnum.Vendedor, username, password);

					if (isLoggedIn) {
						System.out.println("Login realizado com sucesso");
						int opcaoMenuVendedor;
						do {
							System.out.println("1. Criar novo produto" +
									"\n2. Pesquisar produto" +
									"\n3. Atualizar produto" +
									"\n4. Excluir produto" +
									"\n5. Listar vendas" +
									"\n6. Editar Status de envio de uma venda" +
									"\n7. Relatorios de venda" +
									"\n8. Logout");
							System.out.print("Opcao: ");
							opcaoMenuVendedor = Integer.parseInt(sc.nextLine());

							if (opcaoMenuVendedor == 1) { // OK
								System.out.println("Categorias:");
								List<CategoriaModel> categorias = categoriaService.getAllCategorias();
								for (CategoriaModel i : categorias) {
									System.out.println(i.getId() + ". " + i.getNome());
								}
								System.out.print("Escolha o id da categoria: ");
								int idCategoria = Integer.parseInt(sc.nextLine());

								for (CategoriaModel i : categorias) {
									if (i.getId() == idCategoria) {
										System.out.print("Nome do produto: ");
										String nomeProduto = sc.nextLine();
										System.out.print("Preco do produto: ");
										Double precoProduto = Double.parseDouble(sc.nextLine());
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
								int idAtualizacao = Integer.parseInt(sc.nextLine());

								for (ProdutoModel i : produtosUpdate) {
									if (i.getId() == idAtualizacao) {
										System.out.print("Novo nome: ");
										String novoNome = sc.nextLine();
										System.out.print("Novo preco: ");
										Double novoPreco = Double.parseDouble(sc.nextLine());
										i.setNome(novoNome.toUpperCase(Locale.ROOT));
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
								Long idProdutoDelete = Long.parseLong(sc.nextLine());
								for (ProdutoModel i : produtosDelete) {
									if (i.getId() == idProdutoDelete) {
										System.out.println("Quer mesmo deletar o produto?");
										System.out.println("1. Sim" +
												"\n2. Não");
										System.out.print("Opcao: ");
										int confirmarDelete = Integer.parseInt(sc.nextLine());
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
							else if (opcaoMenuVendedor == 5) {
								List<VendaModel> vendas = vendaService.getAllVendas();
								for (VendaModel i : vendas) {
									System.out.println("Id venda: " + i.getId() +
											"\nId do cliente: " + i.getIdCliente() +
											"\nId do produto: " + i.getIdProduto() +
											"\nEndereco de envio: " + i.getEnderecoEnvio() +
											"\nTipo de pagamento: " + i.getTipoPagamento() +
											"\n Valor da venda: " + i.getValorVenda() +
											"\nStatus de envio: " + i.getFoiEnviado());
									System.out.println("-----------------");
								}
							}
							else if (opcaoMenuVendedor == 6) {
								List<VendaModel> vendas = vendaService.getAllVendas();
								for (VendaModel i : vendas) {
									System.out.println("Id venda: " + i.getId() +
											"\nId do cliente: " + i.getIdCliente() +
											"\nId do produto: " + i.getIdProduto() +
											"\nEndereco de envio: " + i.getEnderecoEnvio() +
											"\nTipo de pagamento: " + i.getTipoPagamento() +
											"\n Valor da venda: " + i.getValorVenda() +
											"\nStatus de envio: " + i.getFoiEnviado());
									System.out.println("-----------------");
								}
								System.out.print("Id da venda para mudança do status de envio: ");
								int idVenda = Integer.parseInt(sc.nextLine());
								for (VendaModel i : vendas) {
									if (i.getId() == idVenda) {
										System.out.println("1. Enviado" +
												"\n2. Não enviado");
										System.out.print("Status: ");
										int statusVenda = Integer.parseInt(sc.nextLine());

										if (statusVenda == 1) {
											i.setFoiEnviado(true);
											vendaService.updateVenda(i);
										}
										else if (statusVenda == 2) {
											i.setFoiEnviado(false);
											vendaService.updateVenda(i);
										}
										else {
											System.out.println("Opcao invalida");
										}
									}
								}

							}
							else if (opcaoMenuVendedor == 7) {
								System.out.println("1. Valor total já vendido em todo o período" +
										"\n2. Valor total já vendido por categoria" +
										"\n3. Valor total já vendido por produto");
								System.out.print("Opcao: ");
								int opcaoRelatorio = Integer.parseInt(sc.nextLine());
								if (opcaoRelatorio == 1) {
									Double totalVendas = 0.0;
									for (VendaModel i : vendaService.getAllVendas()) {
										totalVendas += i.getValorVenda();
									}
									System.out.println("Valor total de vendas: R$" + totalVendas);

								}
								else if (opcaoRelatorio == 2) {
									Double totalVendas = 0.0;
									Set<Long> idsCategorias = new HashSet<>();
									List<TotalVendasCategoria> totalVendasCategorias = new ArrayList<>();
									for (VendaModel i : vendaService.getAllVendas()) {
										idsCategorias.add(i.getIdCategoria());
									}
									for (Long i : idsCategorias) {
										totalVendasCategorias.add(new TotalVendasCategoria(i));
									}
									for (VendaModel i : vendaService.getAllVendas()) {
										totalVendas += i.getValorVenda();
										for (TotalVendasCategoria j : totalVendasCategorias) {
											if (i.getIdCategoria().equals(j.getIdCategoria())) {
												j.incrementTotalVendas(i.getValorVenda());
												Optional<CategoriaModel> categoriaOptional = categoriaService.getCategoriaById(i.getIdCategoria());
												CategoriaModel categoria = categoriaOptional.get();
												j.setNomeCategoria(categoria.getNome());
											}
										}
									}
									for (TotalVendasCategoria i : totalVendasCategorias) {
										System.out.println(i.getNomeCategoria() + " - R$" + i.getTotalVendas() + " - " + String.format("%.2f", (i.getTotalVendas() * 100) / totalVendas) + "%");
									}
								}
								else if (opcaoRelatorio == 3) {
									Double totalVendas = 0.0;
									Set<Long> idsProdutos = new HashSet<>();
									List<TotalVendasProdutos> totalVendasProdutos = new ArrayList<>();
									for (VendaModel i : vendaService.getAllVendas()) {
										idsProdutos.add(i.getIdProduto());
									}
									for (Long i : idsProdutos) {
										totalVendasProdutos.add(new TotalVendasProdutos(i));
									}
									for (VendaModel i : vendaService.getAllVendas()) {
										totalVendas += i.getValorVenda();
										for (TotalVendasProdutos j : totalVendasProdutos) {
											if (i.getIdProduto().equals(j.getIdProduto())) {
												j.incrementTotalVendas(i.getValorVenda());
												Optional<ProdutoModel> produtoOptional = produtoService.getProdutoById(i.getIdProduto());
												ProdutoModel produto = produtoOptional.get();
												j.setNomeProduto(produto.getNome());
											}
										}
									}
									for (TotalVendasProdutos i : totalVendasProdutos) { // x = produto . 100 / total
										System.out.println(i.getNomeProduto() + " - R$" + i.getTotalVendas() + " - " + String.format("%.2f", (i.getTotalVendas() * 100) / totalVendas) + "%");
									}
								}
							}
							else {
								System.out.println("opcao invalida.");
							}
						} while(opcaoMenuVendedor != 8);
					}
					else {
						System.out.println("Nao logou");
					}
				}

//----------------------------------------------------LOGIN CLIENTE-----------------------------------------------------
				else if (opcaoLogin == 3) {
					System.out.print("Username: ");
					username = sc.nextLine();
					System.out.print("Password: ");
					password = sc.nextLine();
					isLoggedIn = login(UserTypeEnum.Cliente, username, password);

					if (isLoggedIn) {
						System.out.println("LOGOU LOGOU cliente");
						int opcaoMenuCliente;
						CarrinhoDeCompras carrinhoDeCompras = new CarrinhoDeCompras();
						do {
							System.out.println("1. Navegar no Ecommerce" +
									"\n2. Visualizar compras" +
									"\n3. Logout");
							System.out.print("Opcao: ");
							opcaoMenuCliente = Integer.parseInt(sc.nextLine());
							if (opcaoMenuCliente == 1) {
								int opcaoCompras;
								do {
									System.out.println("1. Adicionar produto" +
											"\n2. Visualizar carrinho" +
											"\n3. Encerrar compras");
									System.out.print("Opcao: ");
									opcaoCompras = Integer.parseInt(sc.nextLine());
									if (opcaoCompras == 1) {
										List<ProdutoModel> produtos = produtoService.getAllProdutos();
										for (ProdutoModel i : produtos) {
											System.out.println("Produtos: ");
											System.out.println(i.getId() + ". " + i.getNome() + " R$" + i.getPreco());
										}
										System.out.println("Deseja adicionar algum produto? ");
										System.out.println("1. Sim" +
												"\n2. Não");
										int adicionarProduto = Integer.parseInt(sc.nextLine());
										if (adicionarProduto == 1 ) {
											System.out.print("Id do produto: ");
											Long idProdutoAdicionar = Long.parseLong(sc.nextLine());
											for (ProdutoModel j : produtos) {
												if (j.getId() == idProdutoAdicionar) {
													carrinhoDeCompras.addProduto(j);
												}
											}
										}
										else if (adicionarProduto == 2) {
											System.out.println("Voltando ao menu");
										}
										else {
											System.out.println("Opcao invalida");
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
										int metodoPagamento = Integer.parseInt(sc.nextLine());
										System.out.println("Endereco de envio: ");
										String enderecoEnvio = sc.nextLine();
										for (ProdutoModel i : carrinhoDeCompras.getProdutos()) {
											if (metodoPagamento == 1) {
												vendaService.createVenda(new VendaModel(
														usuarioService.findUsuarioByNome(username).get(0).getId(), i.getId(), i.getIdCategoria(), TipoPagamentoEnum.BOLETO, enderecoEnvio, i.getPreco()
												));
											}
											else if (metodoPagamento == 2) {
												vendaService.createVenda(new VendaModel(
														usuarioService.findUsuarioByNome(username).get(0).getId(), i.getId(), i.getIdCategoria(), TipoPagamentoEnum.DEBITO, enderecoEnvio, i.getPreco()
												));
											}
											else if (metodoPagamento == 3) {
												vendaService.createVenda(new VendaModel(
														usuarioService.findUsuarioByNome(username).get(0).getId(), i.getId(), i.getIdCategoria(), TipoPagamentoEnum.CREDITO, enderecoEnvio, i.getPreco()
												));
											}
											else if (metodoPagamento == 4) {
												vendaService.createVenda(new VendaModel(
														usuarioService.findUsuarioByNome(username).get(0).getId(), i.getId(), i.getIdCategoria(), TipoPagamentoEnum.PIX, enderecoEnvio, i.getPreco()
												));
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
								List<VendaModel> vendas = vendaService.findVendaByIdCliente(usuarioService.findUsuarioByNome(username).get(0).getId());
								for (VendaModel i : vendas) {
									Optional<ProdutoModel> produtoOptional = produtoService.getProdutoById(i.getIdProduto());
									ProdutoModel produto = produtoOptional.get();
									System.out.println("Produto: " + produto.getNome() +
											"\nR$" + produto.getPreco() +
											"\nEnvio: " + i.getFoiEnviado());
									System.out.println("----------------");
								}
							}
							else if (opcaoMenuCliente == 3) {
								System.out.println("Logout.");
							}
							else {
								System.out.println("Opcao invalida.");
							}
						} while (opcaoMenuCliente != 3);
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
				String username;
				String password;
				System.out.print("Opcao: ");
				int opcaoMenuCadastro = Integer.parseInt(sc.nextLine());

				//CADASTRO DE VENDEDOR
				if (opcaoMenuCadastro == 1) {
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
				else if (opcaoMenuCadastro == 2) {
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
			else if (opcaoMenu == 0) {
				System.out.println("Volte sempre!");
				System.exit(0);
				break;
			}
			else {
				System.out.println("Opcao Invalida.");
			}

		} while (true);
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
