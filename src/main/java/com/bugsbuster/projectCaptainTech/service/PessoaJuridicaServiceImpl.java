package com.bugsbuster.projectCaptainTech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bugsbuster.projectCaptainTech.model.Conta;
import com.bugsbuster.projectCaptainTech.model.PessoaJuridica;
import com.bugsbuster.projectCaptainTech.repository.ContaRepository;
import com.bugsbuster.projectCaptainTech.repository.EnderecoRepository;
import com.bugsbuster.projectCaptainTech.repository.PessoaJuridicaRepository;

@Service
public class PessoaJuridicaServiceImpl {

	@Autowired
	PessoaJuridicaRepository pessoaJuridicaRepository;
	@Autowired
	ContaRepository contaRepository;
	@Autowired
	EnderecoRepository enderecoRepository;

	public Iterable<PessoaJuridica> obterTodos() {
		return this.pessoaJuridicaRepository.findAll();
	}

	public PessoaJuridica obterPorId(int id) {
		return this.pessoaJuridicaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException());
	}

	public PessoaJuridica criarPessoaJuridica(PessoaJuridica pj) {
		PessoaJuridica novaPj = this.pessoaJuridicaRepository.save(pj);
		boolean resp = true;
		while (resp) {
			int randomNumber = (int) (Math.random() * (99999 - 10000) + 10000);
			if (!this.contaRepository.existsByNumero(randomNumber)) {
				Conta conta = new Conta(randomNumber, pj, 0.0);
				this.contaRepository.save(conta);
				resp = false;
			}
		}
		return novaPj;
	}

	public PessoaJuridica atualizar(PessoaJuridica pj) {
		PessoaJuridica newPJ = obterPorId(pj.getId_cliente());

		if (pj.getNomeFantasia() != null) {
			newPJ.setNomeFantasia(pj.getNomeFantasia());
		}

		if (pj.getTelefone() != null) {
			newPJ.setTelefone(pj.getTelefone());
		}

		if (pj.getEmail() != null) {
			newPJ.setEmail(pj.getEmail());
		}

		return this.pessoaJuridicaRepository.save(newPJ);
	}

	public PessoaJuridica desativar(int id) {
		PessoaJuridica newPJ = obterPorId(id);
		newPJ.setAtivo(false);
		return this.pessoaJuridicaRepository.save(newPJ);
	}
	
	public Iterable<PessoaJuridica> obterPorNomeFantasia(String nome){
		return this.pessoaJuridicaRepository.findByNomeFantasiaContaining(nome);
	}
	
	public Iterable<PessoaJuridica> obterPorCnpj(String cnpj){
		return this.pessoaJuridicaRepository.findByCnpj(cnpj);
	}
	
	public Iterable<PessoaJuridica> obterPorTelefone(String telefone){
		return this.pessoaJuridicaRepository.findByTelefone(telefone);
	}
	
	public Iterable<PessoaJuridica> obterPorRazaoSocial(String razao){
		return this.pessoaJuridicaRepository.findByRazaoSocialContaining(razao);
	}
	
	public Iterable<PessoaJuridica> obterPorInscricaoEstadual(String inscricao){
		return this.pessoaJuridicaRepository.findByInscricaoEstadualContaining(inscricao);
	}


	
	
	
	
}
