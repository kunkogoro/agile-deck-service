// package com.aavn.agiledeckserver.game.control;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import javax.persistence.EntityManager;

// import com.aavn.agiledeckserver.game.entity.Game;

// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.Mockito;

// import io.quarkus.test.junit.QuarkusTest;


// @QuarkusTest
// public class GameServiceTest {
    
//     @InjectMocks
//     private GameService gameService;

//     @Mock
//     private EntityManager em;

//     @Test
//     public void givenGameInformation_whenAddNewGame_thenReturnTrue(){
//         Game game = new Game("Big bang", "this is an Agile Deck game");

//         Mockito.doAnswer(invocationOnMock -> {
//             Game persistedGame = invocationOnMock.getArgument(0);
//             persistedGame.setId(1);
//             return null;
//         }).when(this.em).persist(game);
    
//         this.gameService.add(game);

//         assertNotNull(game.getId());
//         assertEquals(1, game.getId());
//     }
// }
