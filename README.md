# GetClan (Klany)

## Wymagania
- **Plugin działa wyłącznie na Paper 1.21.10 (Java 21).**
- Inne wersje (1.21.11, 1.20.x, itp.) nie są wspierane.

## Wiadomości i MiniMessage
Plugin obsługuje tryb hybrydowy wiadomości:
- Jeśli tekst zawiera tagi MiniMessage (`<...>`), zostanie sparsowany przez MiniMessage.
- W pozostałych przypadkach nadal działa format legacy z `&`.

### Przykłady
- MiniMessage: `<gradient:#ff7a18:#af002d>Klany</gradient> <bold>{player}</bold>`
- Legacy: `&aZielona wiadomość &7({player})`

> Placeholdery typu `{player}`, `{clan}`, `{tag}`, `{money}` są podmieniane bezpiecznie.

## Konfiguracja
W `config.yml` dostępna jest sekcja `messages`:

```yml
messages:
  # true = pozwala używać MiniMessage w wiadomościach, ale legacy (&) nadal działa (tryb hybrydowy).
  useMiniMessage: true
```

## Build
```bash
mvn -DskipTests clean package
```
