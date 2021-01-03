package com.victor.cursohibernate;

import com.victor.cursohibernate.domain.*;
import com.victor.cursohibernate.domain.enums.EstadoPagamento;
import com.victor.cursohibernate.domain.enums.TipoCliente;
import com.victor.cursohibernate.repositoriesDAO.*;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CursohibernateApplication implements CommandLineRunner
{

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

	public static void main(String[] args)
	{
		SpringApplication.run(CursohibernateApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "computador", 2000.0);
		Produto p2 = new Produto(null, "impressora", 800.0);
		Produto p3 = new Produto(null, "mouse", 80.0);

		//substituir getProdutos().addAll(...) por setProdutos(...)
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.setCidades(Arrays.asList(c1));
		est2.setCidades(Arrays.asList(c2, c3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria silva", "maria@gmail.com", "19344569704", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("12314521412","132214124321"));

		Endereco e1 = new Endereco(null, "rua flores", "300", "apto 303", "Jardim", "432523523", cli1, c1);
		Endereco e2 = new Endereco(null, "Av amtos", "105", "sala 12", "centro", "1234534", cli1, c2);

		cli1.setEnderecos(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2020 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2020 08:22"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2020 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.setPedidos(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));

	}
}
