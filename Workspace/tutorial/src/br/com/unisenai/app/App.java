package br.com.unisenai.app;

import br.com.unisenai.view.ContactListView;

public class App {

    public static void main(String[] args) {
        // Cria uma instância da classe ContactListView.
        ContactListView view = new ContactListView();
        
        // Torna a janela visível para o usuário.
        view.setVisible(true);
    }
}