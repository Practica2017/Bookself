package
com.example.bookshelfproject.Activity.Book.BookProfile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.bookshelfproject.Activity.Book.BookPopularActivity;
import com.example.bookshelfproject.Model.Book;
import com.example.bookshelfproject.Model.Conversation;
import com.example.bookshelfproject.R;
import com.google.gson.Gson;

/**
 * Created by filip on 8/16/2017.
 */

public class BookProfileActivity extends AppCompatActivity{

    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_profile);

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());


        viewPager = (ViewPager) findViewById(R.id.container);
        setUpViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setUpViewPager(ViewPager viewPager){
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new BookProfileFragment(), "Profile");
        adapter.addFragment(new BookReviewsFragment(), "Reviews");
        adapter.addFragment(new BookDiscussionFragment(), "Discussion");
        viewPager.setAdapter(adapter);
    }

}
