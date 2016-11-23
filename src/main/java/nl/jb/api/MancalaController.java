package nl.jb.api;

import nl.jb.domain.Board;
import nl.jb.domain.GameStatus;
import nl.jb.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api")
public class MancalaController {

	@Autowired
	private GameService gameService;

	@RequestMapping(method = RequestMethod.POST, path = "/newGame")
	@ResponseStatus(value = HttpStatus.OK)
	public GameStatus newGame(@RequestParam int stones, @RequestParam int pits) {
		return gameService.newGame(stones, pits);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/turn")
	@ResponseStatus(value = HttpStatus.OK)
	public GameStatus doTurn(@RequestParam int player, @RequestParam int pit) {
		return gameService.play(player, pit);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity handleIllegalArgument(IllegalArgumentException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}
}
