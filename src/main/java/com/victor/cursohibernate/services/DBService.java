package com.victor.cursohibernate.services;

import com.victor.cursohibernate.domain.*;
import com.victor.cursohibernate.domain.enums.EstadoPagamento;
import com.victor.cursohibernate.domain.enums.Perfil;
import com.victor.cursohibernate.domain.enums.TipoCliente;
import com.victor.cursohibernate.repositoriesDAO.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DBService
{

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public void instantiateTestDatabase() throws ParseException
	{
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama Mesa e Banho");
		Categoria cat4 = new Categoria(null, "Esportes");
		Categoria cat5 = new Categoria(null, "Saude");
		Categoria cat6 = new Categoria(null, "Escola");
		Categoria cat7 = new Categoria(null, "Limpeza");

		Produto p1 = new Produto(null, "computador", 2000.0);
		Produto p2 = new Produto(null, "impressora", 800.0);
		Produto p3 = new Produto(null, "mouse", 80.0);
		Produto p4 = new Produto(null, "mesa de escritorio", 2000.0);
		Produto p5 = new Produto(null, "toalha", 800.0);
		Produto p6 = new Produto(null, "colcha", 80.0);
		Produto p7 = new Produto(null, "tv true color", 2000.0);
		Produto p8 = new Produto(null, "roçadeira", 800.0);
		Produto p9 = new Produto(null, "abajour", 80.0);
		Produto p10 = new Produto(null, "Pendente", 2000.0);
		Produto p11 = new Produto(null, "Shampoo", 800.0);


		//substituir getProdutos().addAll(...) por setProdutos(...)
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));

		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p4.getCategorias().addAll(Arrays.asList(cat2));
		p5.getCategorias().addAll(Arrays.asList(cat3));
		p6.getCategorias().addAll(Arrays.asList(cat3));
		p7.getCategorias().addAll(Arrays.asList(cat4));
		p8.getCategorias().addAll(Arrays.asList(cat5));
		p9.getCategorias().addAll(Arrays.asList(cat6));
		p10.getCategorias().addAll(Arrays.asList(cat6));
		p11.getCategorias().addAll(Arrays.asList(cat7));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5 , p6, p7, p8, p9, p10, p11));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.setCidades(Arrays.asList(c1));
		est2.setCidades(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria silva", "victor.sk8.santos@gmail.com", "19344569704",
			TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("12314521412", "132214124321"));


		Cliente cli2 = new Cliente(null, "João silva", "victor.sk8.santos2@gmail.com", "32792460083",
				TipoCliente.PESSOAFISICA, pe.encode("123"));
		cli2.addPerfil(Perfil.ADMIN);
		cli2.getTelefones().addAll(Arrays.asList("5234234", "234234"));

		Endereco e1 = new Endereco(null, "rua flores", "300", "apto 303", "Jardim", "432523523", cli1,
			c1);
		Endereco e2 = new Endereco(null, "Av amtos", "105", "sala 12", "centro", "1234534", cli1, c2);

		Endereco e3 = new Endereco(null, "Av floriano", "777", "", "sul", "98765", cli2, c2);

		cli1.setEnderecos(Arrays.asList(e1, e2));
		cli2.setEnderecos(Arrays.asList(e3));

		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1, e2, e3));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2020 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2020 08:22"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2,
			sdf.parse("20/10/2020 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.setPedidos(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.setItens(Stream.of(ip1, ip2).collect(Collectors.toSet()));
		ped2.setItens(Stream.of(ip3).collect(Collectors.toSet()));

		p1.setItens(Stream.of(ip1).collect(Collectors.toSet()));
		p2.setItens(Stream.of(ip3).collect(Collectors.toSet()));
		p3.setItens(Stream.of(ip2).collect(Collectors.toSet()));

		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
	}
}
