package com.castrodev.wishlist;

import com.castrodev.wishlist.detail.DetailPresenterImpl;
import com.castrodev.wishlist.detail.DetailView;
import com.castrodev.wishlist.model.Location;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

/**
 * Created by rodrigocastro on 12/04/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class DetailPresenterTest {

    @Mock
    DetailView view;

    private DetailPresenterImpl presenter;

    @Before
    public void setUp() throws Exception {
        presenter = new DetailPresenterImpl(view);
    }

    @Test
    public void checkRegister_validData() {
        presenter.validateData("Playstation 4", "24/04/2017", "I need it quickly", new Location("Ponta Porã", 0.0, 0.0), "1250", "observation");
        verify(view).showProgress();
        verify(view).navigateToHome();
    }

    @Test
    public void checkRegister_emptyWhat() {
        presenter.validateData("", "24/04/2017", "I need it quickly", new Location("Ponta Porã", 0.0, 0.0), "1250", "observation");
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).setWhatError();
    }

    @Test
    public void checkRegister_emptyWhen() {
        presenter.validateData("Playstation 4", "", "I need it quickly", new Location("Ponta Porã", 0.0, 0.0), "1250", "observation");
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).setWhenError();
    }

    @Test
    public void checkRegister_emptyWhy() {
        presenter.validateData("Playstation 4", "24/04/2017", "", new Location("Ponta Porã", 0.0, 0.0), "1250", "observation");
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).setWhyError();
    }

    @Test
    public void checkRegister_emptyWhere() {
        presenter.validateData("Playstation 4", "24/04/2017", "I need it quickly", new Location("", 0.0, 0.0), "1250", "observation");
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).setWhereError();
    }

    @Test
    public void checkRegister_emptyHowMuch() {
        presenter.validateData("Playstation 4", "24/04/2017", "I need it quickly", new Location("Ponta Porã", 0.0, 0.0), "", "observation");
        verify(view).showProgress();
        verify(view).hideProgress();
        verify(view).setHowMuchError();
    }




}
