package com.mohsenoid.rickandmorty.view.character.list;

import com.mohsenoid.rickandmorty.data.DataCallback;
import com.mohsenoid.rickandmorty.domain.Repository;
import com.mohsenoid.rickandmorty.model.CharacterModel;
import com.mohsenoid.rickandmorty.test.CharacterDataFactory;
import com.mohsenoid.rickandmorty.test.DataFactory;
import com.mohsenoid.rickandmorty.util.config.ConfigProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CharacterListPresenterTest {

    @Mock
    Repository repository;

    @Mock
    ConfigProvider configProvider;

    @Mock
    CharacterListContract.View view;

    private CharacterListContract.Presenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        presenter = new CharacterListPresenter(repository, configProvider);
        presenter.bind(view);
    }

    @Test
    public void testLoadCharactersCallsViewShowLoading() {
        // GIVEN
        stubConfigProviderIsOnline(true);

        // WHEN
        presenter.loadCharacters();

        // THEN
        verify(view, times(1)).showLoading();
    }

    @Test
    public void testLoadCharactersCallsViewShowOfflineMessageWhenIsNotOnline() {
        // GIVEN
        stubConfigProviderIsOnline(false);

        // WHEN
        presenter.loadCharacters();

        // THEN
        verify(view, times(1)).showOfflineMessage(false);
    }

    @Test
    public void testLoadCharactersCallsRepositoryQueryCharacters() {
        // GIVEN
        stubConfigProviderIsOnline(true);

        List<Integer> characterIds = new ArrayList<>();
        characterIds.add(DataFactory.randomInt());
        characterIds.add(DataFactory.randomInt());
        characterIds.add(DataFactory.randomInt());

        presenter.setCharacterIds(characterIds);

        // WHEN
        presenter.loadCharacters();

        // THEN
        verify(repository, times(1)).queryCharacters(eq(characterIds), any());
    }

    @Test
    public void testLoadCharactersCallsViewSetCharactersOnSuccess() {
        // GIVEN
        List<CharacterModel> characters = CharacterDataFactory.makeCharactersList(5);
        stubRepositoryQueryCharactersOnSuccess(characters);

        // WHEN
        presenter.loadCharacters();

        // THEN
        verify(view, times(1)).setCharacters(eq(characters));
    }

    @Test
    public void testLoadCharactersCallsHideLoadingOnSuccess() {
        // GIVEN
        List<CharacterModel> characters = CharacterDataFactory.makeCharactersList(5);
        stubRepositoryQueryCharactersOnSuccess(characters);

        // WHEN
        presenter.loadCharacters();

        // THEN
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void testKillCharacterCallsRepositoryWhenCharacterIsAlive() {
        // GIVEN
        stubConfigProviderIsOnline(true);
        CharacterModel character = CharacterDataFactory.makeCharacter();
        character.setStatus(CharacterModel.ALIVE);

        // WHEN
        presenter.killCharacter(character);

        // THEN
        verify(repository, times(1)).killCharacter(eq(character.getId()), any());
    }

    @Test
    public void testLoadCharactersCallsShowMessageOnError() {
        // GIVEN
        String errorMessage = DataFactory.randomString();
        stubRepositoryQueryCharactersOnError(new Exception(errorMessage));

        // WHEN
        presenter.loadCharacters();

        // THEN
        verify(view, times(1)).showMessage(errorMessage);
    }

    @Test
    public void testLoadCharactersCallsHideLoadingOnError() {
        // GIVEN
        stubRepositoryQueryCharactersOnError(new Exception());

        // WHEN
        presenter.loadCharacters();

        // THEN
        verify(view, times(1)).hideLoading();
    }

    @Test
    public void testKillCharacterSkipCallingRepositoryWhenCharacterIsKilled() {
        // GIVEN
        stubConfigProviderIsOnline(true);
        CharacterModel character = CharacterDataFactory.makeCharacter();
        character.setIsKilledByUser(true);

        // WHEN
        presenter.killCharacter(character);

        // THEN
        verify(repository, times(0)).killCharacter(eq(character.getId()), any());
    }

    private void stubConfigProviderIsOnline(boolean isOnline) {
        when(configProvider.isOnline())
                .thenReturn(isOnline);
    }

    private void stubRepositoryQueryCharactersOnSuccess(List<CharacterModel> characters) {
        doAnswer((Answer<Void>) invocation -> {
            DataCallback<List<CharacterModel>> callback = invocation.getArgument(1);
            callback.onSuccess(characters);

            return null;
        }).when(repository).queryCharacters(any(), any());
    }

    private void stubRepositoryQueryCharactersOnError(Exception exception) {
        doAnswer((Answer<Void>) invocation -> {
            DataCallback<List<CharacterModel>> callback = invocation.getArgument(1);
            callback.onError(exception);

            return null;
        }).when(repository).queryCharacters(any(), any());
    }
}
