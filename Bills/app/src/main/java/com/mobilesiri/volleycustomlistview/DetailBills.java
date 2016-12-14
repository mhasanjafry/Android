package com.mobilesiri.volleycustomlistview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
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
import com.mobilesiri.model.Bills;

import java.util.List;

public class DetailBills extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_bills);

        Intent i = getIntent();
        final Bills bill = (Bills) i.getSerializableExtra("bills");

        TextView billidval = (TextView)findViewById(R.id.billidval);
        billidval.setText(((bill.getID()).isEmpty())?"N.A." : bill.getID());

        TextView billid = (TextView)findViewById(R.id.billid);
        billid.setText("Bill ID:");

        TextView titleval = (TextView)findViewById(R.id.titleval);
        titleval.setText(((bill.getshorttitle()).isEmpty())?"N.A." : bill.getshorttitle());

        TextView title = (TextView)findViewById(R.id.title);
        title.setText("Title:");

        TextView typeval = (TextView)findViewById(R.id.typeval);
        typeval.setText(((bill.gettype()).isEmpty())?"N.A." : bill.gettype());

        TextView type = (TextView)findViewById(R.id.type);
        type.setText("Bill Type:");

        TextView sponsorval = (TextView)findViewById(R.id.sponsorval);
        sponsorval.setText(((bill.getsponsor()).isEmpty())?"N.A." : bill.getsponsor());

        TextView sponsor = (TextView)findViewById(R.id.sponsor);
        sponsor.setText("Sponsor:");

        TextView Chamber = (TextView)findViewById(R.id.chamber);
        Chamber.setText("Chamber:");

        TextView ChamberValue = (TextView)findViewById(R.id.chamberval);
        ChamberValue.setText(((bill.getChamber()).isEmpty())?"N.A." : bill.getChamber());

        TextView status = (TextView)findViewById(R.id.status);
        status.setText("Status:");

        TextView statusval = (TextView)findViewById(R.id.statusval);
        statusval.setText(bill.getBillStatus());

        TextView intro = (TextView)findViewById(R.id.intro);
        intro.setText("Introduced On:");

        TextView introval = (TextView)findViewById(R.id.introval);
        introval.setText(((bill.getintroduced_on()).isEmpty())?"N.A." : bill.getintroduced_on());

        TextView congress = (TextView)findViewById(R.id.congress);
        congress.setText("Congress URL:");

        TextView congressval = (TextView)findViewById(R.id.congressval);
        congressval.setText(((bill.getWebsite()).isEmpty())?"N.A." : bill.getWebsite());

        TextView version = (TextView)findViewById(R.id.version);
        version.setText("Version Status:");
//
        TextView versionval = (TextView)findViewById(R.id.versionval);
        versionval.setText(((bill.getversion()).isEmpty())?"N.A." : bill.getversion());

        TextView pdf = (TextView)findViewById(R.id.pdf);
        pdf.setText("Bill URL:");

        TextView pdfval = (TextView)findViewById(R.id.pdfval);
        pdfval.setText(((bill.getpdf()).isEmpty())?"N.A." : bill.getpdf());
    }
}
