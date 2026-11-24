# v1 : Solution directe

Cette solution utilise des méthodes `synchronized` avec `wait()` et `notifyAll()`. Le buffer est un tableau de `Message` de taille bornée, utilisé de façon circulaire (indices `in` et `out`). Les variables `nempty` (cases libres) et `nfull` (messages disponibles) contrôlent l'accès au buffer.

### Exécution
1. Se placer dans le dossier racine `produc-consum`
2. Compiler : `javac -d bin prodcons/v1/*.java`
3. Exécuter : `java -cp bin:. prodcons.v1.Main`
4. Les paramètres (nombre de producteurs/consommateurs, taille du buffer, temps…) peuvent être modifiés dans `prodcons/options.xml`

### Tableau gardes-actions

| Opération | Pré-action | Garde | Post-action |
|---|---|---|---|
| `put(Message m)` | — | `nempty > 0` | `buf[in] = m; in = (in+1) % N; nempty--; nfull++;` |
| `Message get()` | — | `nfull > 0` | `m = buf[out]; out = (out+1) % N; nfull--; nempty++;` |

### Pseudo-code

```
put(m) {
    // on attend qu'il y ait une case libre
    while (nempty == 0) {
        wait();
    }
    // 1. écrire le message dans le buffer
    // 2. mettre à jour nempty et nfull
    // 3. notifyAll()
}

get() {
    // on attend qu'il y ait un message à consommer
    while (nfull == 0) {
        wait();
    }
    // 1. prendre le message du buffer
    // 2. mettre à jour nempty et nfull
    // 3. notifyAll()
    // 4. retourner le message
}
```

On utilise `while` (et non `if`) car, au réveil, la condition peut ne plus être vraie : un autre thread a pu passer avant.