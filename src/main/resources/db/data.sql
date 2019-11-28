-- populate cards table with Aces just for testing

INSERT INTO `blackjack`.`cards` (`id`, `img`, `type`, `value`, `deck_id`) VALUES ('1', 'https://drive.google.com/open?id=12-sSFteUfKpHM2F83RThgSl9mLC5AIhs', 'DIAMONDS', 'ACE', '1');
INSERT INTO `blackjack`.`cards` (`id`, `img`, `type`, `value`, `deck_id`) VALUES ('2', 'https://drive.google.com/open?id=1IwerIZZOQr7bEX73tdGhVqrTRTMwYrmx', 'SPADES', 'ACE', '1');
INSERT INTO `blackjack`.`cards` (`id`, `img`, `type`, `value`, `deck_id`) VALUES ('3', 'https://drive.google.com/open?id=1X0gAYzd6YmdcaPJ2vwf_NGJ6P80n3n8s', 'CLUBS', 'ACE', '1');
INSERT INTO `blackjack`.`cards` (`id`, `img`, `type`, `value`, `deck_id`) VALUES ('4', 'https://drive.google.com/open?id=1G0st20ZmgxItlIki1pB5xHkZ77ernGQX', 'HEARTS', 'ACE', '1');


-- populate decks table for testing
INSERT INTO `blackjack`.`decks` (`id`) VALUES ('1');
