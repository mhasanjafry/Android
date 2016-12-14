package com.mobilesiri.volleycustomlistview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.mobilesiri.model.Legislators;

import java.util.List;

public class DetailLegislator extends AppCompatActivity {

    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_legislator);

        Intent i = getIntent();
        final Legislators legis = (Legislators)i.getSerializableExtra("legislator");

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();

        NetworkImageView webicon = (NetworkImageView) findViewById(R.id.websitepic);
        webicon.setImageUrl("http://cs-server.usc.edu:45678/hw/hw8/images/w.png", imageLoader);
        webicon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (!((legis.getWebsite()).isEmpty())){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(legis.getWebsite()));
                    startActivity(intent);
                }else{
                    Toast.makeText(DetailLegislator.this, "Website does not exist!", Toast.LENGTH_LONG).show();
                }
            }
        });

        NetworkImageView fbicon = (NetworkImageView) findViewById(R.id.facebookpic);
        fbicon.setImageUrl("http://cs-server.usc.edu:45678/hw/hw8/images/f.png", imageLoader);
        fbicon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (!((legis.getFacebook()).isEmpty())){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(legis.getFacebook()));
                    startActivity(intent);
                }else{
                    Toast.makeText(DetailLegislator.this, "Facebook account does not exist!", Toast.LENGTH_LONG).show();
                }
            }
        });

        NetworkImageView twittericon = (NetworkImageView) findViewById(R.id.twitterpic);
        twittericon.setImageUrl("http://cs-server.usc.edu:45678/hw/hw8/images/t.png", imageLoader);
        twittericon.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (!((legis.getTwitter()).isEmpty())){
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(legis.getTwitter()));
                    startActivity(intent);
                }else{
                    Toast.makeText(DetailLegislator.this, "Twitter account does not exist!", Toast.LENGTH_LONG).show();
                }
            }
        });

        NetworkImageView legisPic = (NetworkImageView) findViewById(R.id.detailpic);
        legisPic.setImageUrl(legis.getImgURL(), imageLoader);

        NetworkImageView partyPic = (NetworkImageView) findViewById(R.id.partypic);
        partyPic.setImageUrl(legis.getPartyPic(), imageLoader);

        TextView partyName = (TextView)findViewById(R.id.partyName);
        partyName.setText(((legis.getParty()).isEmpty())?"N.A." : legis.getParty());

        TextView Name = (TextView)findViewById(R.id.name);
        Name.setText("Name:");

        TextView NameValue = (TextView)findViewById(R.id.nameval);
        NameValue.setText(((legis.getFullName()).isEmpty())?"N.A." : legis.getFullName());

        TextView Email = (TextView)findViewById(R.id.email);
        Email.setText("Email:");

        TextView EmailValue = (TextView)findViewById(R.id.emailval);
        EmailValue.setText(((legis.getEmail()).isEmpty())?"N.A." : legis.getEmail());

        TextView Chamber = (TextView)findViewById(R.id.chamber);
        Chamber.setText("Chamber:");

        TextView ChamberValue = (TextView)findViewById(R.id.chamberval);
        ChamberValue.setText(((legis.getChamber()).isEmpty())?"N.A." : legis.getChamber());

        TextView Contact = (TextView)findViewById(R.id.contact);
        Contact.setText("Contact:");

        TextView ContactValue = (TextView)findViewById(R.id.contactval);
        ContactValue.setText(((legis.getContact()).isEmpty())?"N.A." : legis.getContact());

        TextView StartTerm = (TextView)findViewById(R.id.start);
        StartTerm.setText("Start Term:");

        TextView StartTermValue = (TextView)findViewById(R.id.startval);
        StartTermValue.setText(((legis.getStart_term()).isEmpty())?"N.A." : legis.getStart_term());

        TextView EndTerm = (TextView)findViewById(R.id.end);
        EndTerm.setText("End Term:");

        TextView EndTermValue = (TextView)findViewById(R.id.endval);
        EndTermValue.setText(((legis.getEnd_term()).isEmpty())?"N.A." : legis.getEnd_term());

        TextView Term = (TextView)findViewById(R.id.term);
        Term.setText("Term:");
//
        ProgressBar progress = (ProgressBar)findViewById(R.id.termval);
        progress.setProgress(legis.getProgress());

        TextView Office = (TextView)findViewById(R.id.office);
        Office.setText("Office:");

        TextView OfficeValue = (TextView)findViewById(R.id.officeval);
        OfficeValue.setText(((legis.getOffice()).isEmpty())?"N.A." : legis.getOffice());

        TextView State = (TextView)findViewById(R.id.state);
        State.setText("State:");

        TextView StateValue = (TextView)findViewById(R.id.stateval);
        StateValue.setText(((legis.getState()).isEmpty())?"N.A." : legis.getState());

        TextView Fax = (TextView)findViewById(R.id.fax);
        Fax.setText("Fax:");

        TextView FaxValue = (TextView)findViewById(R.id.faxval);
        FaxValue.setText(((legis.getFax()).isEmpty())?"N.A." : legis.getFax());

        TextView Birthday = (TextView)findViewById(R.id.birthday);
        Birthday.setText("Birthday:");

        TextView BirthdayValue = (TextView)findViewById(R.id.birthdayval);
        BirthdayValue.setText(((legis.getBirthday()).isEmpty())?"N.A." : legis.getBirthday());
//        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_detail_legislator);
//        layout.addView(textView);
    }
}
