package com.product.tabletmanager.viewmodel;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.product.tabletmanager.model.Drug;
import com.product.tabletmanager.model.room.RoomRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class DrugViewModelTest {
    private DrugViewModel drugViewModel;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    private RoomRepository repository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        /*drugViewModel = new DrugViewModel(repository);*/ //todo
    }

    @Test
    public void testSaveDrugWithBlankName() {
/*        drugViewModel.mForm = Drug.FORM.CAPSULE;
        drugViewModel.mName = "";*/ //todo
        boolean save = drugViewModel.canSaveDrug();
        assertEquals(false, save);
    }
}