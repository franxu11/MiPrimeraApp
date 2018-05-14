package com.android.teaching.miprimeraapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.teaching.miprimeraapp.model.GameModel;
import com.android.teaching.miprimeraapp.presenters.GameDetailPresenter;
import com.android.teaching.miprimeraapp.view.GameDetailView;

public class GameDetailActivity extends AppCompatActivity
    implements GameDetailView {

    private GameDetailPresenter presenter;
    private int currentGameId;
    private String currentGameWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new GameDetailPresenter();

        currentGameId = getIntent().getIntExtra("game_id", 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startPresenting(this);
        presenter.loadGameWithId(currentGameId);
    }

    @Override
    public void onGameLoaded(GameModel game) {
        // UPDATE VIEW WITH GAME MODEL DATA
        ImageView icono = findViewById(R.id.game_icon);
        icono.setImageResource(game.getIconDrawable());

        // 1. CAMBIAR IMAGEN DE FONDO
        LinearLayout fondoLayout = findViewById(R.id.game_image_container);
        fondoLayout.setBackgroundResource(game.getBackgroundDrawable());

        // 2. CAMBIAR DESCRIPCION
        TextView descriptionTextView = findViewById(R.id.game_description);
        descriptionTextView.setText(game.getDescription());

        // 3. CAMBIAR TITULO DE LA TOOLBAR
        getSupportActionBar().setTitle(game.getName());

        this.currentGameWebsite = game.getOfficialWebsiteUrl();
    }

    public void goToWebsite(View view) {
        Intent websiteIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(currentGameWebsite));
        startActivity(websiteIntent);
    }
}























