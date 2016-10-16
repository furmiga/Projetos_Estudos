package br.com.alura.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.agenda.ListaAlunosActivity;
import br.com.alura.agenda.R;
import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by Furmiga on 24/05/2016.
 */
public class AlunosAdapter extends BaseAdapter{
    private final List<Aluno> alunos;
    private final Context context;

    public AlunosAdapter(List<Aluno> alunos, Context context) {
        this.alunos = alunos;
        this.context = context;
    }
    

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Aluno aluno = alunos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        //aqui eu é uma questão de performance, pois ele ira carregas a quantidade de itens que irão ficar visiveis na tela, e quando eu for rolando a pagina,
        //ele vai pegar aqueles itens que não estão mais visiveis para utilizar, logo se tem 10000 de itens, eu não vou criar tudo isso
        View view = convertView;
        if(convertView == null) {
            //o ultimo parametro false, é para ele não colocar direto na tela este inflate, pq se não quando o usuario for colocar, o app dara uma exceção
            view = inflater.inflate(R.layout.list_item, parent, false);
        }
        //FIM
        TextView campoNome = (TextView) view.findViewById(R.id.item_nome);
        campoNome.setText(aluno.getNome());

        TextView campoTelefone = (TextView) view.findViewById(R.id.item_telefone);
        campoTelefone.setText(aluno.getTelefone());


        ImageView campoFoto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = aluno.getCaminhoFoto();
        if(caminhoFoto!=null) {
            //assim que a foto for salva eu tranformo ela em um bitmap
            Bitmap bitMap = BitmapFactory.decodeFile(caminhoFoto);
            //o ultimo parametro true é um filtro posto na imagem, para amenizar a perpecpção da queda de qualidade
            Bitmap bitMapReduzido = bitMap.createScaledBitmap(bitMap, 100, 100, true);
            campoFoto.setImageBitmap(bitMapReduzido);
            //Com isso eu informo que quero que a imagem se encaixe no image view, tanto com relação a altura quanto a largura
            campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
        }

        return view;
    }
}
