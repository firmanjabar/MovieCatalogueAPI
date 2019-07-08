import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Movie movies = getIntent().getParcelableExtra("movies");

        ImageView detail_img_bd = findViewById(R.id.detail_img_bd);
        Glide.with(this)
                .load(movies.getImgBD())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(detail_img_bd);
        ImageView detail_img_actor = findViewById(R.id.detail_img_actor);
        Glide.with(this)
                .load(movies.getImgActor())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(detail_img_actor);
        ImageView detail_img_poster = findViewById(R.id.detail_img_poster);
        Glide.with(this)
                .load(movies.getImgPoster())
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(detail_img_poster);
        TextView detail_title = findViewById(R.id.detail_title);
        TextView detail_year = findViewById(R.id.detail_year);
        TextView detail_description = findViewById(R.id.detail_description);
        TextView detail_director = findViewById(R.id.detail_director);
        TextView detail_actor = findViewById(R.id.detail_actor);

        int imgPoster = movies.getImgPoster();
        int imgBD = movies.getImgBD();
        int imgActor = movies.getImgActor();
        String title = movies.getTitle();
        String year = movies.getYear();
        String description = movies.getDescription ();
        String director = movies.getDirector();
        String actor = movies.getActor();

        detail_img_poster.setImageResource(imgPoster);
        detail_img_bd.setImageResource(imgBD);
        detail_img_actor.setImageResource(imgActor);
        detail_title.setText(title);
        detail_year.setText(year);
        detail_description.setText(description);
        detail_director.setText(director);
        detail_actor.setText(actor);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(movies.getTitle());
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}