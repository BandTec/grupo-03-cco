package br.com.bandtec.projeto.pneu.clienteJira;

import br.com.bandtec.projeto.pneu.modelos.Issue;
import br.com.bandtec.projeto.pneu.modelos.UsuarioLogado;

import java.io.IOException;

public class ConexaoAPIJira {
    
    private static String email = UsuarioLogado.getEmail();
    private static String url = "pneu-duck.atlassian.net";
    private static String token = "5By0fA4ynKitjeXjfuhFAC91";
    public static void criacao(Issue novaIssue) throws IOException {
        ClienteJiraApi clienteJiraApi = new ClienteJiraApi(
                url,
                email,
                token
        );
        clienteJiraApi.criarIssue(novaIssue);
    }
}
