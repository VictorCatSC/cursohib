package com.victor.cursohibernate.repositoriesDAO;


import com.victor.cursohibernate.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer>
{

}
