import java.lang.AssertionError;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.Set;
import java.util.HashSet;
import java.util.List;

/*
 * Aquesta entrega consisteix en implementar tots els mètodes annotats amb el comentari "// TO DO".
 *
 * L'avaluació consistirà en:
 *
 * - Si el codi no compila, la nota del grup serà de 0.
 *
 * - Principalment, el correcte funcionament de cada mètode (provant amb diferents entrades). Teniu
 *   alguns exemples al mètode `main`.
 *
 * - Tendrem en compte la neteja i organització del codi. Un estandard que podeu seguir és la guia
 *   d'estil de Google per Java: https://google.github.io/styleguide/javaguide.html.  Algunes
 *   consideracions importants: indentació i espaiat consistent, bona nomenclatura de variables,
 *   declarar les variables el més aprop possible al primer ús (és a dir, evitau blocs de
 *   declaracions). També convé utilitzar el for-each (for (int x : ...)) enlloc del clàssic (for
 *   (int i = 0; ...)) sempre que no necessiteu l'índex del recorregut.
 *
 * Per com està plantejada aquesta entrega, no necessitau (ni podeu) utilitzar cap `import`
 * addicional, ni mètodes de classes que no estiguin ja importades. El que sí podeu fer és definir
 * tots els mètodes addicionals que volgueu (de manera ordenada i dins el tema que pertoqui).
 *
 * Podeu fer aquesta entrega en grups de com a màxim 3 persones, i necessitareu com a minim Java 8.
 * Per entregar, posau a continuació els vostres noms i entregau únicament aquest fitxer.
 * - Nom 1: Lluis Barca Pons
 * - Nom 2:
 * - Nom 3:
 *
 * L'entrega es farà a través d'una tasca a l'Aula Digital que obrirem abans de la data que se us
 * hagui comunicat i vos recomanam que treballeu amb un fork d'aquest repositori per seguir més
 * fàcilment les actualitzacions amb enunciats nous. Si no podeu visualitzar bé algun enunciat,
 * assegurau-vos de que el vostre editor de texte estigui configurat amb codificació UTF-8.
 */
class Entrega {
  /*
   * Aquí teniu els exercicis del Tema 1 (Lògica).
   *
   * Els mètodes reben de paràmetre l'univers (representat com un array) i els
   * predicats adients:
   * (per exemple, `Predicate<Integer> p`). Per avaluar aquest predicat, si `x` és
   * un element de
   * l'univers, podeu fer-ho com `p.test(x)`, que té com resultat un booleà (true
   * si `P(x)` és
   * cert). Els predicats de dues variables són de tipus `BiPredicate<Integer,
   * Integer>` i
   * similarment s'avaluen com `p.test(x, y)`.
   *
   * En cada un d'aquests exercicis us demanam que donat l'univers i els predicats
   * retorneu `true`
   * o `false` segons si la proposició donada és certa (suposau que l'univers és
   * suficientment
   * petit com per poder provar tots els casos que faci falta).
   */
  static class Tema1 {
    /*
     * És cert que ∀x ∃!y. P(x) -> Q(x,y) ?
     */
    static boolean exercici1(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      for (int x : universe) {
        boolean found = false;
        for (int y : universe) {
          // Si P(x) és cert i Q(x, y) és cert, llavors la proposició és falsa
          if (p.test(x) && q.test(x, y)) {
            // Si ja hem trobat un y vàlida, llavors la proposició és falsa
            if (found) {
              return false;
            } else {
              found = true;
            }
          }
        }

        // Si P(x) és cert i no hem trobat cap y que compleixi la condició, llavors la
        // proposició és falsa
        if (!found && p.test(x)) {
          return false;
        }
      }

      return true;
    }

    /*
     * És cert que ∃!x ∀y. P(y) -> Q(x,y) ?
     */
    static boolean exercici2(int[] universe, Predicate<Integer> p, BiPredicate<Integer, Integer> q) {
      int count = 0;
      for (int x : universe) {
        boolean valid = true;
        for (int y : universe) {
          // Si P(y) és cert i Q(x, y) és fals, llavors la proposició és falsa
          if (p.test(y) && !q.test(x, y)) {
            valid = false;
            break;
          }
        }

        // Si P(y) cert i Q(x, y) cert per tots els y, llavors la proposició és certa
        if (valid) {
          count++;
          // Si ja hem trobat més d'un x vàlida, llavors la proposició és falsa
          if (count > 1) {
            return false;
          }
        }
      }

      return count == 1;
    }

    /*
     * És cert que ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?
     */
    static boolean exercici3(int[] universe, BiPredicate<Integer, Integer> p, BiPredicate<Integer, Integer> q) {
      for (int x : universe) {
        for (int y : universe) {
          boolean existsXYForAllZ = true;
          for (int z : universe) {
            // Si P(x, z) i Q(y, z) tenen el mateix valor per tots els z, llavors la
            // proposició és falsa
            if (!(p.test(x, z) ^ q.test(y, z))) {
              existsXYForAllZ = false;
              break;
            }
          }

          // Si P(x, z) i Q(y, z) tenen valors diferents per algun z, llavors la
          // proposició és certa
          if (existsXYForAllZ) {
            return true;
          }
        }
      }

      return false;
    }

    /*
     * És cert que (∀x. P(x)) -> (∀x. Q(x)) ?
     *
     * ∃x. ¬P(x) ∨ (∀x. Q(x))
     */
    static boolean exercici4(int[] universe, Predicate<Integer> p, Predicate<Integer> q) {
      boolean existsXNotP = false;
      for (int x : universe) {
        // Si P(x) és fals, llavors la proposició és certa
        if (!p.test(x)) {
          existsXNotP = true;
          break;
        }
      }

      // Si existeix un x tal que P(x) és fals, llavors la proposició és certa
      if (existsXNotP) {
        return true;
      }

      // Si P(x) és cert per tots els x i Q(x) és fals per algun x, llavors la
      // proposició és falsa
      for (int x : universe) {
        if (!q.test(x)) {
          return false;
        }
      }

      return true;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
     * `main`)
     */
    static void tests() {
      // Exercici 1
      // ∀x ∃!y. P(x) -> Q(x,y) ?

      assertThat(
          exercici1(
              new int[] { 2, 3, 5, 6 },
              x -> x != 4,
              (x, y) -> x == y));

      assertThat(
          !exercici1(
              new int[] { -2, -1, 0, 1, 2, 3 },
              x -> x != 0,
              (x, y) -> x * y == 1));

      // Exercici 2
      // ∃!x ∀y. P(y) -> Q(x,y) ?

      assertThat(
          exercici2(
              new int[] { -1, 1, 2, 3, 4 },
              y -> y <= 0,
              (x, y) -> x == -y));

      assertThat(
          !exercici2(
              new int[] { -2, -1, 1, 2, 3, 4 },
              y -> y < 0,
              (x, y) -> x * y == 1));

      // Exercici 3
      // ∃x,y ∀z. P(x,z) ⊕ Q(y,z) ?

      assertThat(
          exercici3(
              new int[] { 2, 3, 4, 5, 6, 7, 8 },
              (x, z) -> z % x == 0,
              (y, z) -> z % y == 1));

      assertThat(
          !exercici3(
              new int[] { 2, 3 },
              (x, z) -> z % x == 1,
              (y, z) -> z % y == 1));

      // Exercici 4
      // (∀x. P(x)) -> (∀x. Q(x)) ?

      assertThat(
          exercici4(
              new int[] { 0, 1, 2, 3, 4, 5, 8, 9, 16 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0 // x és múltiple de 4
          ));

      assertThat(
          !exercici4(
              new int[] { 0, 2, 4, 6, 8, 16 },
              x -> x % 2 == 0, // x és múltiple de 2
              x -> x % 4 == 0 // x és múltiple de 4
          ));
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 2 (Conjunts).
   *
   * Per senzillesa tractarem els conjunts com arrays (sense elements repetits).
   * Per tant, un
   * conjunt de conjunts d'enters tendrà tipus int[][].
   *
   * Les relacions també les representarem com arrays de dues dimensions, on la
   * segona dimensió
   * només té dos elements. Per exemple
   * int[][] rel = {{0,0}, {1,1}, {0,1}, {2,2}};
   * i també donarem el conjunt on està definida, per exemple
   * int[] a = {0,1,2};
   *
   * Les funcions f : A -> B (on A i B son subconjunts dels enters) les
   * representam donant el domini
   * int[] a, el codomini int[] b, i f un objecte de tipus Function<Integer,
   * Integer> que podeu
   * avaluar com f.apply(x) (on x és d'a i el resultat f.apply(x) és de b).
   */
  static class Tema2 {
    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static boolean exercici1(int[] a, int[][] rel) {
      return isReflexive(a, rel) && isSymmetric(rel) && isTransitive(rel);
    }

    /**
     * Aquest mètode comprova si la relació `rel` és reflexiva. És a dir,
     * si tots els elements de `a` estan relacionats amb ells mateixos.
     *
     * @param a   l'array que representa el conjunt
     * @param rel la relació
     *
     * @return true si la relació és reflexiva, false altrament
     */
    static boolean isReflexive(int[] a, int[][] rel) {
      for (int value : a) {
        boolean found = false;
        for (int[] pair : rel) {
          if (pair[0] == value && pair[1] == value) {
            found = true;
            break;
          }
        }

        if (!found) {
          return false;
        }
      }

      return true;
    }

    /**
     * Aquest mètode comprova si la relació `rel` és simètrica. És a dir,
     * si per cada parell (x, y) de `rel`, també hi ha el parell (y, x).
     *
     * @param rel la relació
     *
     * @return true si la relació és simètrica, false altrament
     */
    static boolean isSymmetric(int[][] rel) {
      for (int[] pair : rel) {
        boolean found = false;
        for (int[] otherPair : rel) {
          if (pair[0] == otherPair[1] && pair[1] == otherPair[0]) {
            found = true;
            break;
          }
        }

        if (!found) {
          return false;
        }
      }

      return true;
    }

    /**
     * Aquest mètode comprova si la relació `rel` és transitiva. És a dir,
     * si per cada parell (x, y) i (y, z) de `rel`, també hi ha el parell (x, z).
     *
     * @param rel la relació
     *
     * @return true si la relació és transitiva, false altrament
     */
    static boolean isTransitive(int[][] rel) {
      for (int[] pair : rel) {
        for (int[] otherPair : rel) {
          if (pair[1] == otherPair[0]) {
            boolean found = false;
            for (int[] finalPair : rel) {
              if (finalPair[0] == pair[0] && finalPair[1] == otherPair[1]) {
                found = true;
                break;
              }
            }

            if (!found) {
              return false;
            }
          }
        }
      }

      return true;
    }

    /*
     * Comprovau si la relació `rel` definida sobre `a` és d'equivalència. Si ho és,
     * retornau el cardinal del conjunt quocient de `a` sobre `rel`. Si no, retornau
     * -1.
     *
     * Podeu soposar que `a` està ordenat de menor a major.
     */
    static int exercici2(int[] a, int[][] rel) {
      // Comprovau si la relació és d'equivalència
      if (!isReflexive(a, rel) || !isSymmetric(rel) || !isTransitive(rel)) {
        return -1;
      }

      // Cream un array per representar a quina classe d'equivalència pertany cada
      // element
      int n = a[a.length - 1] + 1;
      int[] parent = new int[n];
      for (int i = 0; i < n; i++) {
        // Inicialitzam cada element a la seva pròpia classe d'equivalència
        parent[i] = i;
      }

      // Per cada parell (x, y) de la relació, unim les classes d'equivalència de x, y
      for (int[] pair : rel) {
        int root1 = find(pair[0], parent);
        int root2 = find(pair[1], parent);
        if (root1 != root2) {
          parent[root1] = root2;
        }
      }

      // Contam el nombre de classes d'equivalència diferents
      Set<Integer> uniqueRoots = new HashSet<>();
      for (int element : a) {
        uniqueRoots.add(find(element, parent));
      }

      return uniqueRoots.size();
    }

    /**
     * Aquest mètode retorna la classe d'equivalència de l'element `element`.
     *
     * @param element l'element
     * @param parent  l'array que representa el conjunt
     *
     * @return la classe d'equivalència de l'element
     */
    static int find(int element, int[] parent) {
      if (parent[element] != element) {
        parent[element] = find(parent[element], parent);
      }

      return parent[element];
    }

    /*
     * Comprovau si la relació `rel` definida entre `a` i `b` és una funció.
     *
     * Podeu soposar que `a` i `b` estan ordenats de menor a major.
     */
    static boolean exercici3(int[] a, int[] b, int[][] rel) {
      // Comprovam que la relació no té elements repetits
      for (int i = 0; i < a.length; i++) {
        int count = 0;
        // Contam el nombre de vegades que apareix l'element a[i] a la relació
        for (int j = 0; j < rel.length; j++) {
          if (rel[j][0] == a[i]) {
            count++;
          }
        }

        // Si un element apareix més d'una vegada, llavors la relació no és una funció
        if (count != 1) {
          return false;
        }
      }

      return true;
    }

    /*
     * Suposau que `f` és una funció amb domini `dom` i codomini `codom`. Retornau:
     * - Si és exhaustiva, el màxim cardinal de l'antiimatge de cada element de
     * `codom`.
     * - Si no, si és injectiva, el cardinal de l'imatge de `f` menys el cardinal de
     * `codom`.
     * - En qualsevol altre cas, retornau 0.
     *
     * Podeu suposar que `dom` i `codom` estàn ordenats de menor a major.
     */
    static int exercici4(int[] dom, int[] codom, Function<Integer, Integer> f) {
      int n = codom[codom.length - 1] + 1;
      int[] image = new int[n];
      boolean[] existsInCodom = new boolean[n];

      // Contam el nombre d'elements de 'dom' que van a cada element de 'codom'
      for (int element : dom) {
        int value = f.apply(element);
        image[value]++;
      }

      // Comprovam quins elements de 'codom' són imatge d'algun element de 'dom'
      for (int element : codom) {
        existsInCodom[element] = true;
      }

      int maxImage = 0;
      boolean isInjective = true;
      boolean isSurjective = true;
      int codomCount = 0;
      int imageCount = 0;

      // Cercam el màxim cardinal de l'antiimatge de cada element de 'codom' i
      // comprovam si la funció és injectiva i/o exhaustiva
      for (int i = 0; i < n; i++) {
        if (image[i] > maxImage) {
          maxImage = image[i];
        }
        if (image[i] > 1) {
          isInjective = false;
        }
        if (existsInCodom[i]) {
          if (image[i] == 0) {
            isSurjective = false;
          }
          codomCount++;
        }
        if (image[i] > 0) {
          imageCount++;
        }
      }

      // Retornam el màxim cardinal de l'antiimatge de cada element de 'codom' si la
      // funció és exhaustiva, el cardinal de l'imatge de 'f' menys el cardinal de
      // 'codom' si la funció és injectiva, 0 altrament
      if (isSurjective) {
        return maxImage;
      } else if (isInjective) {
        return imageCount - codomCount;
      } else {
        return 0;
      }
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
     * `main`)
     */
    static void tests() {
      // Exercici 1
      // `rel` és d'equivalencia?

      assertThat(
          exercici1(
              new int[] { 0, 1, 2, 3 },
              new int[][] { { 0, 0 }, { 1, 1 }, { 2, 2 }, { 3, 3 }, { 1, 3 }, { 3, 1 } }));

      assertThat(
          !exercici1(
              new int[] { 0, 1, 2, 3 },
              new int[][] { { 0, 0 }, { 1, 1 }, { 2, 2 }, { 3, 3 }, { 1, 2 }, { 1, 3 }, { 2, 1 },
                  { 3, 1 } }));

      // Exercici 2
      // si `rel` és d'equivalència, quants d'elements té el seu quocient?

      final int[] int09 = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

      assertThat(
          exercici2(
              int09,
              generateRel(int09, int09, (x, y) -> x % 3 == y % 3)) == 3);

      assertThat(
          exercici2(
              new int[] { 1, 2, 3 },
              new int[][] { { 1, 1 }, { 2, 2 } }) == -1);

      // Exercici 3
      // `rel` és una funció?

      final int[] int05 = { 0, 1, 2, 3, 4, 5 };

      assertThat(
          exercici3(
              int05,
              int09,
              generateRel(int05, int09, (x, y) -> x == y)));

      assertThat(
          !exercici3(
              int05,
              int09,
              generateRel(int05, int09, (x, y) -> x == y / 2)));

      // Exercici 4
      // el major |f^-1(y)| de cada y de `codom` si f és exhaustiva
      // sino, |im f| - |codom| si és injectiva
      // sino, 0

      assertThat(
          exercici4(
              int09,
              int05,
              x -> x / 4) == 0);

      assertThat(
          exercici4(
              int05,
              int09,
              x -> x + 3) == int05.length - int09.length);

      assertThat(
          exercici4(
              int05,
              int05,
              x -> (x + 3) % 6) == 1);
    }

    /// Genera un array int[][] amb els elements {a, b} (a de as, b de bs) que
    /// satisfàn pred.test(a, b)
    static int[][] generateRel(int[] as, int[] bs, BiPredicate<Integer, Integer> pred) {
      ArrayList<int[]> rel = new ArrayList<>();

      for (int a : as) {
        for (int b : bs) {
          if (pred.test(a, b)) {
            rel.add(new int[] { a, b });
          }
        }
      }

      return rel.toArray(new int[][] {});
    }
  }

  /*
   * Aquí teniu els exercicis del Tema 3 (Grafs).
   *
   * Donarem els grafs en forma de diccionari d'adjacència, és a dir, un graf serà
   * un array
   * on cada element i-èssim serà un array ordenat que contendrà els índexos dels
   * vèrtexos adjacents
   * al i-èssim vèrtex. Per exemple, el graf cicle C_3 vendria donat per
   *
   * int[][] g = {{1,2}, {0,2}, {0,1}} (no dirigit: v0 -> {v1, v2}, v1 -> {v0,
   * v2}, v2 -> {v0,v1})
   * int[][] g = {{1}, {2}, {0}} (dirigit: v0 -> {v1}, v1 -> {v2}, v2 -> {v0})
   *
   * Podeu suposar que cap dels grafs té llaços.
   */
  static class Tema3 {
    /*
     * Retornau l'ordre menys la mida del graf (no dirigit).
     */
    static int exercici1(int[][] g) {
      int ordre = g.length;
      int mida = 0;

      // Contam el nombre d'arestes
      for (int i = 0; i < ordre; i++) {
        mida += g[i].length;
      }

      return ordre - mida / 2;
    }

    /*
     * Suposau que el graf (no dirigit) és connex. És bipartit?
     */
    static boolean exercici2(int[][] g) {
      int ordre = g.length;
      int[] colors = new int[ordre];
      boolean bipartit = true;

      for (int i = 0; i < ordre; i++) {
        if (colors[i] == 0) {
          if (!dfsBipartite(g, i, colors, 1)) {
            bipartit = false;
            break;
          }
        }
      }

      return bipartit;
    }

    /**
     * Mètode auxiliar per comprovar si un graf és bipartit. És a dir, si es pot
     * pintar amb dos colors de manera que dos vèrtexs adjacents no tenguin el
     * mateix color.
     *
     * @param g         el graf
     * @param vertex    el vèrtex actual
     * @param color     l'array que representa els colors dels vèrtexs
     * @param currColor el color actual
     *
     * @return true si el graf és bipartit, false altrament
     */
    static boolean dfsBipartite(int[][] g, int vertex, int[] color, int currColor) {
      color[vertex] = currColor;

      for (int adj : g[vertex]) {
        if (color[adj] == 0) {
          if (!dfsBipartite(g, adj, color, 3 - currColor)) {
            return false;
          }
        } else if (color[adj] == currColor) {
          return false;
        }
      }

      return true;
    }

    /*
     * Suposau que el graf és un DAG. Retornau el nombre de descendents amb grau de
     * sortida 0 del vèrtex i-èssim.
     */
    static int exercici3(int[][] g, int i) {
      boolean[] visited = new boolean[g.length];
      return dfsCountLeafNodes(g, i, visited);
    }

    /**
     * Mètode auxiliar per comptar el nombre de descendents amb grau de sortida 0
     * d'un vèrtex.
     *
     * @param g       el graf
     * @param vertex  el vèrtex actual
     * @param visited l'array que representa els vèrtexs visitats
     *
     * @return el nombre de descendents amb grau de sortida 0 del vèrtex
     */
    static int dfsCountLeafNodes(int[][] g, int vertex, boolean[] visited) {
      visited[vertex] = true;

      // Si el vèrtex no té adjacents, llavors és una fulla
      if (g[vertex].length == 0) {
        return 1;
      }

      // Contam el nombre de descendents amb grau de sortida 0
      int count = 0;
      for (int adj : g[vertex]) {
        if (!visited[adj]) {
          count += dfsCountLeafNodes(g, adj, visited);
        }
      }

      return count;
    }

    /*
     * Donat un arbre arrelat (dirigit, suposau que l'arrel es el vèrtex 0),
     * trobau-ne el diàmetre del graf subjacent.
     * Suposau que totes les arestes tenen pes 1.
     */
    static int exercici4(int[][] g) {
      return -1;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
     * `main`)
     */
    static void tests() {
      final int[][] undirectedK6 = {
          { 1, 2, 3, 4, 5 },
          { 0, 2, 3, 4, 5 },
          { 0, 1, 3, 4, 5 },
          { 0, 1, 2, 4, 5 },
          { 0, 1, 2, 3, 5 },
          { 0, 1, 2, 3, 4 },
      };

      /*
       * 1
       * 4 0 2
       * 3
       */
      final int[][] undirectedW4 = {
          { 1, 2, 3, 4 },
          { 0, 2, 4 },
          { 0, 1, 3 },
          { 0, 2, 4 },
          { 0, 1, 3 },
      };

      // 0, 1, 2 | 3, 4
      final int[][] undirectedK23 = {
          { 3, 4 },
          { 3, 4 },
          { 3, 4 },
          { 0, 1, 2 },
          { 0, 1, 2 },
      };

      /*
       * 7
       * 0
       * 1 2
       * 3 8
       * 4
       * 5 6
       */
      final int[][] directedG1 = {
          { 1, 2 }, // 0
          { 3 }, // 1
          { 3, 8 }, // 2
          { 4 }, // 3
          { 5, 6 }, // 4
          {}, // 5
          {}, // 6
          { 0 }, // 7
          {},
      };

      /*
       * 0
       * 1 2 3
       * 4 5 6
       * 7 8
       */

      final int[][] directedRTree1 = {
          { 1, 2, 3 }, // 0 = r
          {}, // 1
          { 4, 5 }, // 2
          { 6 }, // 3
          { 7, 8 }, // 4
          {}, // 5
          {}, // 6
          {}, // 7
          {}, // 8
      };

      /*
       * 0
       * 1
       * 2 3
       * 4 5
       * 6 7
       */

      final int[][] directedRTree2 = {
          { 1 },
          { 2, 3 },
          {},
          { 4, 5 },
          {},
          { 6, 7 },
          {},
          {},
      };

      assertThat(exercici1(undirectedK6) == 6 - 5 * 6 / 2);
      assertThat(exercici1(undirectedW4) == 5 - 2 * 4);

      assertThat(exercici2(undirectedK23));
      assertThat(!exercici2(undirectedK6));

      assertThat(exercici3(directedG1, 0) == 3);
      assertThat(exercici3(directedRTree1, 2) == 3);

      // assertThat(exercici4(directedRTree1) == 5);
      // assertThat(exercici4(directedRTree2) == 4);
    }
  }

  static class Tema4 {
    /*
     * Donau la solució de l'equació
     *
     * ax ≡ b (mod n),
     *
     * Els paràmetres `a` i `b` poden ser negatius (`b` pot ser zero), però podeu
     * suposar que n > 1.
     *
     * Si la solució és x ≡ c (mod m), retornau `new int[] { c, m }`, amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici1(int a, int b, int n) {
      a = ((a % n) + n) % n; // Asseguram que a és positiu
      b = ((b % n) + n) % n; // Asserguram que b és positiu

      int mcd = mcd(a, n);

      // Si el mcd no divideix b, llavors no hi ha solució
      if (b % mcd != 0) {
        return null;
      }

      // Trobem l'invers modular
      int inv = modInverse(a / mcd, n / mcd);

      // Si l'invers modular no existeix, llavors no hi ha solució
      if (inv == -1) {
        return null;
      }

      // Multiplicam l'invers modular per b/mcd per obtenir la solució
      int x = (inv * (b / mcd)) % (n / mcd);

      return new int[] { x, n / mcd };
    }

    /**
     * Mètode auxiliar per calcular el màxim comú divisor de dos nombres.
     *
     * @param a el primer nombre
     * @param b el segon nombre
     *
     * @return el màxim comú divisor de a i b
     */
    static int mcd(int a, int b) {
      if (b == 0) {
        return a;
      } else {
        return mcd(b, a % b);
      }
    }

    /**
     * Mètode auxiliar per calcular l'invers modular d'un nombre.
     *
     * @param a el nombre
     * @param m el mòdul
     *
     * @return l'invers modular de a mod m o -1 si no existeix
     */
    static int modInverse(int a, int m) {
      a = a % m;

      for (int x = 1; x < m; x++) {
        if ((a * x) % m == 1) {
          return x;
        }
      }

      return -1;
    }

    /*
     * Donau la solució (totes) del sistema d'equacions
     *
     * { x ≡ b[0] (mod n[0])
     * { x ≡ b[1] (mod n[1])
     * { x ≡ b[2] (mod n[2])
     * { ...
     *
     * Cada b[i] pot ser negatiu o zero, però podeu suposar que n[i] > 1. També
     * podeu suposar
     * que els dos arrays tenen la mateixa longitud.
     *
     * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`,
     * amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici2a(int[] b, int[] n) {
      int len = b.length;
      long N = 1;

      // Calculam el producte de tots els mòduls
      for (int ni : n) {
        N *= ni;
      }

      long x = 0;
      // Per cada congruència, calculam el producte de tots els mòduls excepte el
      // mòdul actual
      for (int i = 0; i < len; i++) {
        long Ni = N / n[i];
        long inv = modInverse(Ni, n[i]);

        // Si l'invers modular no existeix, llavors no hi ha solució
        if (inv == -1) {
          return null;
        }

        // Solució
        x += ((b[i] % n[i] + n[i]) % n[i]) * Ni * inv;
        x %= N;
      }

      return new int[] { (int) x, (int) N };
    }

    /**
     * Mètode auxiliar per calcular l'invers modular d'un nombre.
     *
     * @param a el nombre de tipus long
     * @param m el mòdul de tipus long
     *
     * @return l'invers modular de a mod m o -1 si no existeix
     */
    static long modInverse(long a, long m) {
      a = a % m;

      for (long x = 1; x < m; x++) {
        if ((a * x) % m == 1) {
          return x;
        }
      }

      return -1;
    }

    /*
     * Donau la solució (totes) del sistema d'equacions
     *
     * { a[0]·x ≡ b[0] (mod n[0])
     * { a[1]·x ≡ b[1] (mod n[1])
     * { a[2]·x ≡ b[2] (mod n[2])
     * { ...
     *
     * Cada a[i] o b[i] pot ser negatiu (b[i] pot ser zero), però podeu suposar que
     * n[i] > 1. També
     * podeu suposar que els tres arrays tenen la mateixa longitud.
     *
     * Si la solució és de la forma x ≡ c (mod m), retornau `new int[] { c, m }`,
     * amb 0 ⩽ c < m.
     * Si no en té, retornau null.
     */
    static int[] exercici2b(int[] a, int[] b, int[] n) {
      return null;
    }

    /*
     * Suposau que n > 1. Donau-ne la seva descomposició en nombres primers,
     * ordenada de menor a
     * major, on cada primer apareix tantes vegades com el seu ordre. Per exemple,
     *
     * exercici4a(300) --> new int[] { 2, 2, 3, 5, 5 }
     *
     * No fa falta que cerqueu algorismes avançats de factorització, podeu utilitzar
     * la força bruta
     * (el que coneixeu com el mètode manual d'anar provant).
     */
    static ArrayList<Integer> exercici3a(int n) {
      ArrayList<Integer> factors = new ArrayList<>();

      // Dividim n pel menor factor primer que trobem
      for (int i = 2; i <= n; i++) {
        // Si i és un factor primer, llavors dividim n per i
        while (n % i == 0) {
          factors.add(i);
          n /= i;
        }
      }

      return factors;
    }

    /*
     * Retornau el nombre d'elements invertibles a Z mòdul n³.
     *
     * Alerta: podeu suposar que el resultat hi cap a un int (32 bits a Java), però
     * n³ no té perquè.
     * De fet, no doneu per suposat que pogueu tractar res més gran que el resultat.
     *
     * No podeu utilitzar ni `long` ni `double` per solucionar aquest problema.
     * Necessitareu
     * l'exercici 3a.
     */
    static int exercici3b(int n) {
      return -1;
    }

    /*
     * Aquí teniu alguns exemples i proves relacionades amb aquests exercicis (vegeu
     * `main`)
     */
    static void tests() {
      assertThat(Arrays.equals(exercici1(17, 1, 30), new int[] { 23, 30 }));
      assertThat(Arrays.equals(exercici1(-2, -4, 6), new int[] { 2, 3 }));
      assertThat(exercici1(2, 3, 6) == null);

      assertThat(
          exercici2a(
              new int[] { 1, 0 },
              new int[] { 2, 4 }) == null);

      assertThat(
          Arrays.equals(
              exercici2a(
                  new int[] { 3, -1, 2 },
                  new int[] { 5, 8, 9 }),
              new int[] { 263, 360 }));

      // assertThat(
      // exercici2b(
      // new int[] { 1, 1 },
      // new int[] { 1, 0 },
      // new int[] { 2, 4 }) == null);

      // assertThat(
      // Arrays.equals(
      // exercici2b(
      // new int[] { 2, -1, 5 },
      // new int[] { 6, 1, 1 },
      // new int[] { 10, 8, 9 }),
      // new int[] { 263, 360 }));

      assertThat(exercici3a(10).equals(List.of(2, 5)));
      assertThat(exercici3a(1291).equals(List.of(1291)));
      assertThat(exercici3a(1292).equals(List.of(2, 2, 17, 19)));

      // assertThat(exercici3b(10) == 400);

      // Aquí 1292³ ocupa més de 32 bits amb el signe, però es pot resoldre sense
      // calcular n³.
      // assertThat(exercici3b(1292) == 961_496_064);

      // Aquest exemple té el resultat fora de rang
      // assertThat(exercici3b(1291) == 2_150_018_490);
    }
  }

  /*
   * Aquest mètode `main` conté alguns exemples de paràmetres i dels resultats que
   * haurien de donar
   * els exercicis. Podeu utilitzar-los de guia i també en podeu afegir d'altres
   * (no els tendrem en
   * compte, però és molt recomanable).
   *
   * Podeu aprofitar el mètode `assertThat` per comprovar fàcilment que un valor
   * sigui `true`.
   */
  public static void main(String[] args) {
    Tema1.tests();
    Tema2.tests();
    Tema3.tests();
    Tema4.tests();
  }

  /// Si b és cert, no fa res. Si b és fals, llança una excepció (AssertionError).
  static void assertThat(boolean b) {
    if (!b)
      throw new AssertionError();
  }
}

// vim: set textwidth=100 shiftwidth=2 expandtab :
