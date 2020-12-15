package br.com.bandtec.projeto.pneu.clienteJira;
import br.com.bandtec.projeto.pneu.modelos.Issue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

public class ConexaoAPIJira{

    public static void criacao(Issue novaIssue, String msg, String titulo) throws IOException {

        // Este "gson" Ã© opcional. Apenas para imprimir os objetos na saÃ­da padrÃ£o, caso queira.


        ClienteJiraApi clienteJiraApi = new ClienteJiraApi(
                "pneu-duck.atlassian.net",
                "jennifer.silva@bandtec.com.br",
                "5By0fA4ynKitjeXjfuhFAC91"
        );
//        clienteJiraApi.criarIssue(novaIssue);

//
    Gson gson = new Gson();
//            Issue issue = clienteJiraApi.getIssue("BDJ-12");
//            System.out.println("Issue recuperada: "+gson.toJson(issue));
//
//         Exemplo de objeto para novo chamado (Issue)
        novaIssue = new Issue();
        novaIssue.setProjectKey("BDJ");
        novaIssue.setSummary(titulo);
        novaIssue.setDescription(msg);
        novaIssue.setLabels("alerta-cpu", "alerta-disco");
        
        clienteJiraApi.criarIssue(novaIssue);
        System.out.println("Issue criada: "+gson.toJson(novaIssue));

    }
//    public static void main(String[] args) throws IOException {
////        ClienteJiraApi clienteJiraApi = new ClienteJiraApi(
////            "pneu-duck.atlassian.net",
////            "jennifer.silva@bandtec.com.br",
////            "5By0fA4ynKitjeXjfuhFAC91"
////        );
////        Gson gson = new Gson();
//          Issue novaIssue = new Issue();
////        novaIssue.setProjectKey("BDJ");
////        novaIssue.setSummary("Uso crítico de CPU e Disco");
////        novaIssue.setDescription("CPU em 99% \nDisco em 89% \nMemória em 45.6%");
////        novaIssue.setLabels("alerta-cpu", "alerta-disco");
////        
////        clienteJiraApi.criarIssue(novaIssue);
////        System.out.println("Issue criada: "+gson.toJson(novaIssue));
//          criacao(novaIssue);
//    }
}