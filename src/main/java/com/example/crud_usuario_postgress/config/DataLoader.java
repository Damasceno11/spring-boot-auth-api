package com.example.crud_usuario_postgress.config;

import com.example.crud_usuario_postgress.model.Endereco;
import com.example.crud_usuario_postgress.model.Produto;
import com.example.crud_usuario_postgress.model.Usuario;
import com.example.crud_usuario_postgress.model.enums.Role;
import com.example.crud_usuario_postgress.repository.ProdutoRepository;
import com.example.crud_usuario_postgress.repository.UsuarioRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final ProdutoRepository produtoRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(UsuarioRepository usuarioRepository, ProdutoRepository produtoRepository, PasswordEncoder passwordEncoder){
    this.usuarioRepository = usuarioRepository;
    this.produtoRepository = produtoRepository;
    this.passwordEncoder = passwordEncoder;
    }

@Override
public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            System.out.println("Carregando dados iniciais no banco de dados...");

            // 1. Criando e salvando Produtos primeiro, pois não dependem de ninguém.
            Produto prod1 = new Produto(null, "Notebook Gamer", new BigDecimal("7500.00"), new HashSet<>());
            Produto prod2 = new Produto(null, "Mouse sem Fio", new BigDecimal("150.00"), new HashSet<>());
            Produto prod3 = new Produto(null, "Teclado Mecânico", new BigDecimal("300.00"), new HashSet<>());
            Produto prod4 = new Produto(null, "Monitor 4K", new BigDecimal("2000.00"), new HashSet<>());
            Produto prod5 = new Produto(null, "Headset RGB", new BigDecimal("250.00"), new HashSet<>());
            Produto prod6 = new Produto(null, "Cadeira Gamer", new BigDecimal("1200.00"), new HashSet<>());

            List<Produto> produtosSalvos = produtoRepository.saveAll(Arrays.asList(prod1,prod2,prod3,prod4,prod5,prod6));

            // 2. Agora criando Usuários
            Usuario user1 = new Usuario(null, "Pedro Damasceno", "pedrodamasceno@gmail.com", "pedro1107", passwordEncoder.encode("damasceno"), Role.ROLE_ADMIN, new HashSet<>(), new HashSet<>());
            Usuario user2 = new Usuario(null, "Bruno Souza", "bruno.souza@email.com", "bruno456", passwordEncoder.encode("senhaBruno"), Role.ROLE_USER, new HashSet<>(), new HashSet<>());
            Usuario user3 = new Usuario(null, "Carla Pereira", "carla_pereira@gmail.com", "carla789", passwordEncoder.encode("senhaCarla"), Role.ROLE_USER, new HashSet<>(), new HashSet<>());
            Usuario user4 = new Usuario(null, "Daniel Costa", "daniel.costa@hotmail.com", "daniel321", passwordEncoder.encode("senhaDaniel"), Role.ROLE_USER, new HashSet<>(), new HashSet<>());
            Usuario user5 = new Usuario(null, "Eva Lima", "evalima@email.com", "eva654", passwordEncoder.encode("senhaEva"), Role.ROLE_USER, new HashSet<>(), new HashSet<>());
            Usuario user6 = new Usuario(null, "Felipe Rocha", "felipe.rocha@hotmail.com", "felipe987", passwordEncoder.encode("senhaFelipe"), Role.ROLE_USER, new HashSet<>(), new HashSet<>());
            Usuario user7 = new Usuario(null, "Alice Silva", "alice_silva@gmail.com", "alice123", passwordEncoder.encode("senhaAlice"), Role.ROLE_USER, new HashSet<>(), new HashSet<>());

            // 3. Criar e Associar Endereços aos Usuários
            Endereco end1User1 = new Endereco(null, "Rua Joaquim Moser", "127", "", "Água Verde", "Blumenau", "SC", "89042-040", user1);
            user1.getEnderecos().add(end1User1);

            Endereco end1User2 = new Endereco(null, "Rua das Acácias", "789", "Casa", "Vila Nova", "Rio de Janeiro", "RJ", "34567-890", user2);
            user2.getEnderecos().add(end1User2);

            Endereco end1User3 = new Endereco(null, "Avenida Paulista", "1000", "Apto 101", "Bela Vista", "São Paulo", "SP", "45678-901", user3);
            user3.getEnderecos().add(end1User3);

            Endereco end1User4 = new Endereco(null, "Rua do Sol", "321", "", "Centro", "Belo Horizonte", "MG", "56789-012", user4);
            Endereco end2User4 = new Endereco(null, "Avenida das Américas", "654", "Apto 202", "Barra da Tijuca", "Rio de Janeiro", "RJ", "67890-123", user4);
            user4.getEnderecos().addAll(Arrays.asList(end1User4,end2User4));

            Endereco end1User5 = new Endereco(null, "Rua das Palmeiras", "987", "Casa", "Centro", "Curitiba", "PR", "78901-234", user5);
            user5.getEnderecos().add(end1User5);

            Endereco end1User6 = new Endereco(null, "Avenida Central", "159", "Apto 303", "Jardim América", "Salvador", "BA", "89012-345", user6);
            user6.getEnderecos().add(end1User6);

            Endereco end1User7 = new Endereco(null, "Rua das Flores", "123", "Apto 45", "Centro", "São Paulo", "SP", "12345-678", user1);
            Endereco end2User7 = new Endereco(null, "Avenida Brasil", "456", "", "Jardins", "São Paulo", "SP", "23456-789", user1);
            user7.getEnderecos().addAll(Arrays.asList(end1User7, end2User7));

            // 4. Associar Produtos aos Usuários
            user1.getProdutos().addAll(Arrays.asList(produtosSalvos.get(0), produtosSalvos.get(2)));
            user2.getProdutos().add(produtosSalvos.get(2));
            user3.getProdutos().add(produtosSalvos.get(5));
            user4.getProdutos().addAll(Arrays.asList(produtosSalvos.get(2), produtosSalvos.get(3), produtosSalvos.get(4)));
            user5.getProdutos().add(produtosSalvos.get(3));
            user6.getProdutos().addAll(Arrays.asList(produtosSalvos.get(1), produtosSalvos.get(2), produtosSalvos.get(3), produtosSalvos.get(4)));
            user7.getProdutos().add(produtosSalvos.get(1));

            // 5. Salvar Usuários (os endereços serão salvos em cascata)
            usuarioRepository.saveAll(List.of(user1,user2,user3,user4,user5,user6,user7));

            System.out.println("Dados iniciais carregados com sucesso!");


        }

}


}



