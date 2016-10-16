package br.com.alura.agenda;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.alura.agenda.converter.AlunoConverter;
import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by Furmiga on 25/05/2016.
 */
public class EnviaAlunosTask extends AsyncTask<Void,String,String> {
    private Context context;
    private ProgressDialog alertDialog;
    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = ProgressDialog.show(context,"Aguarde" , "Enviando para o servidor ...", true, true);
    }

    @Override
    protected String doInBackground(Void[] params) {
        AlunoDAO dao = new AlunoDAO(context);
        List<Aluno> alunos = dao.buscaAlunos();

        AlunoConverter conversor = new AlunoConverter();
        String json = conversor.converteParaJSON(alunos);

        WebClient client = new WebClient();
        String resposta = client.post(json);

        return resposta;
    }


    //este metodo sera executado na thread principal
    @Override
    protected void onPostExecute(String resposta) {
        alertDialog.dismiss();

        Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
