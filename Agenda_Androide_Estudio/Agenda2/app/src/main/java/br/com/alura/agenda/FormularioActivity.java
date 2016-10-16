package br.com.alura.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.alura.agenda.dao.AlunoDAO;
import br.com.alura.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    public static final int CODIGO_CAMERA = 567;
    private FormularioHelper helper;
    private String caminhoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        helper = new FormularioHelper(this);

        //este getIntent é referencia da tela que me chamou
        Intent intent = getIntent();
        //aqui eu pego um valor passado da tela anterior
        Aluno aluno = (Aluno) intent.getSerializableExtra("aluno");
        if(aluno != null) {
            helper.preencheFormulario(aluno);
        }

        Button botaoFoto = (Button) findViewById(R.id.formulario_botao_foto);
        botaoFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MediaStore.ACTION_IMAGE_CAPTURE, significa que eu quero capturar uma imagem, ai o usuario que escolhe qual app usar para isso(geralemnte é a camera mesmo)
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //Caminho da foto(getExternalFilesDir(null) é o caminho reservado para o meu app , onde posso gravar algumas coisas(neste caso uma foto))
                caminhoFoto = getExternalFilesDir(null) +  "/" + System.currentTimeMillis() + ".jpg";
                File arquivoFoto = new File(caminhoFoto);
                //aqui passamos o caminho da foto(MediaStore.EXTRA_OUTPUT  este id aqui é uma convencao que tod aplicativo de captura de imagem segue)
                intentCamera.putExtra(MediaStore.EXTRA_OUTPUT , Uri.fromFile(arquivoFoto));
                //Ao finalizar a execução ele ira chamar o metodo onActivityResult(logo abaixo), e o ultimo parametro é o requestCode
                startActivityForResult(intentCamera, CODIGO_CAMERA);
            }
        });

    }

    //é chamado quando uma startActivityForResult é executada
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //VERIFICA SE A AÇÃO DE TIRAR A FOTO FOI EXECUTADA, POIS O USUARIO PODE ENTRAR NA CAMERA E CANCELAR O PROCESSO
        //CASO ISSO ACONTECESSE O CAMINHO DA FOTO SERIA NULO, E AO TENTAR REDIMENCIONAR O BITMAP IRIA OCORRER UMA EXCEPTION
          //if(requestCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                helper.carregaImagem(caminhoFoto);
            }
       // }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok:
                Aluno aluno = helper.pegaAluno();

                AlunoDAO dao = new AlunoDAO(this);
                if(aluno.getId() != null) {
                    dao.altera(aluno);
                } else {
                    dao.insere(aluno);
                }
                dao.close();

                Toast.makeText(FormularioActivity.this, "Aluno " + aluno.getNome() + " salvo!",
                        Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
