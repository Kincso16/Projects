#include "elsobbsegi_sor.h"

using namespace std;

int main()
{
    PriorityQueue Varoterem_fej;
    PriorityQueue Varoterem_torzs;
    PriorityQueue Varoterem_vegtagok;

    cout << "Kinyilt az orvosi rendelo: " << endl;

    srand(time(NULL));

    string nev;
    int sulyossag;
    int sorszam;
    pair<int, string> beteg;
    bool mehet = true;
    int seged1, seged2, seged3, seged4;
    int* osszstatisztika = new int[10] {0};// 0. felvett betegek; 1. elhalalozott; 2. cserelt prioritast; 3. varolista mutatas; 4. orvos alltal kezelt beteg; 5. megunta a beteg a varakozast; 6. kerdeztek ki kovetkezik; 7. komoly ellatas; 8. nem annyira surgos; 9. csak egy papirert jott
    int* fejstat = new int[10] {0};
    int* torzsstat = new int[10] {0};
    int* vegtagokstat = new int[10] {0};
    int random;

    while (mehet == true) {
        cout << "Ezek kozul tudsz valasztani: " << endl;
        cout << "1. Felvenni uj beteget (a varoterembe csak 50 ember fer el)" << endl;
        cout << "2. Megvaltozott a beteg allapota" << endl;
        cout << "3. Megunta a varakozast" << endl;
        cout << "4. Kovetkezo beteget fogadja az orvos" << endl;
        cout << "5. Mutassa a varolistat" << endl;
        cout << "6. Ki kovetkezik az orvosnal" << endl;
        cout << "7. Bezarjuk a rendelot" << endl;
        cout << "Miben tudok segiteni? Kerem a valaszat:";
        int valasz;
        cin >> valasz;
        random = rand() % 100 + 1;       //plot twist
        if (random % 25 == 0) {
            cout << "Beutott a covid-19 megint." << endl;
            valasz = 7;
        }
        switch (valasz)
        {
        case 1:
            system("cls");
            
            cout << "Mi a neve? Varom a valaszat: ";
            cin >> nev;

            sulyossag = rand() % 100 + 1;

            osszstatisztika[0]++;

            cout << "A test melyik tajekan van a gond? (1. fej; 2. has; 3. kez; 4. lab; 5.sziv) Varom a valaszat:  ";
            cin >> seged1;
            if (seged1 == 1 && sulyossag <= 80) {
                sulyossag = sulyossag + 20;
            }
            else if (seged1 == 5 && sulyossag <= 85) {
                sulyossag += 15;
            }
            else if (seged1 == 2 && sulyossag <= 90) {
                sulyossag += 10;
            }
            else if (sulyossag <= 95 && (seged1==3 || seged1 == 4)) {
                sulyossag += 5;
            }

            cout << "Valamelyik testresze faj? (1. igen; 2. nem) Varom a valaszat: ";

            cin >> seged2;
            if (seged2 == 1 && sulyossag<=90) {
                sulyossag += 10;
            }
            else if (seged2 == 2 && sulyossag > 10) {
                sulyossag -= 10;
            }

            cout << "Egyeb mellekhatasok? (0. nincs; 1. laz; 2. hanyinger; 3. szedules) Varom a valaszat: ";

            cin >> seged3;

            if (seged3 == 1 && sulyossag <= 90) {
                sulyossag += 10;
            }
            else if (seged3 == 2 && sulyossag <= 95) {
                sulyossag += 5;
            }
            else if (seged3 == 3 && sulyossag <= 85) {
                sulyossag += 15;
            }

            if (sulyossag == 100) {
                cout << "Sajnaljuk on meghal.";
                osszstatisztika[1]++;
                if (seged1 == 1) {
                    fejstat[1]++;
                }
                else if (seged1 == 3 || seged1 == 4) {
                    vegtagokstat[1]++;
                }
                else {
                    torzsstat[1]++;
                }
                break;
            }

            if (sulyossag >= 75) {
                osszstatisztika[7]++;
            }
            else if (sulyossag < 25) {
                osszstatisztika[9]++;
            }
            else {
                osszstatisztika[8]++;
            }
            
            if (seged1 == 1) {
                if (Varoterem_fej.getSize() == 49) {
                    cout << "Bocsanat, tele a varoterem." << endl;
                    break;
                }
                Varoterem_fej.insert(sulyossag, nev);
                fejstat[0]++;
                if (sulyossag >= 75) {
                    fejstat[7]++;
                }
                else if (sulyossag < 25) {
                    fejstat[9]++;
                }
                else {
                    fejstat[8]++;
                }
            }
            else if (seged1 == 3 || seged1 == 4) {
                if (Varoterem_vegtagok.getSize() == 49) {
                    cout << "Bocsanat, tele a varoterem." << endl;
                    break;
                }
                Varoterem_vegtagok.insert(sulyossag, nev);
                vegtagokstat[0]++;
                if (sulyossag >= 75) {
                    vegtagokstat[7]++;
                }
                else if (sulyossag < 25) {
                    vegtagokstat[9]++;
                }
                else {
                    vegtagokstat[8]++;
                }
            }
            else {
                if (Varoterem_torzs.getSize() == 49) {
                    cout << "Bocsanat, tele a varoterem." << endl;
                    break;
                }
                Varoterem_torzs.insert(sulyossag, nev);
                torzsstat[0]++;
                if (sulyossag >= 75) {
                    torzsstat[7]++;
                }
                else if (sulyossag < 25) {
                    torzsstat[9]++;
                }
                else {
                    torzsstat[8]++;
                }
            }
            

            cout << "Rogzitettuk a varolistan." << endl;
            break;
        case 2:
            system("cls");
            cout << "Melyik orvoshoz var?(1. fej; 2. torzs; 3. vegtagok) Varom a valaszat: ";
            cin >> seged4;

            if (seged4 == 1) {
                if (Varoterem_fej.getSize() == -1) {
                    cout << "Bocsanat, nem lehetseges ez az opcio." << endl;
                    break;
                }
                cout << "Mutatom a varakozasi listat:" << endl;
                Varoterem_fej.printQueue();
                cout << "Hanyas sorszama van a listan? Varom a valaszat: ";
                cin >> sorszam;
                sorszam--;
                if (sorszam > Varoterem_fej.getSize()) {
                    cout << "Bocsanat, nem talaltunk ilyen sorszamu embert a rendeloben." << endl;
                    break;
                }

                sulyossag = rand() % 100 + 1;

                
                if (sulyossag <= 80) {
                    sulyossag = sulyossag + 20;
                }

                cout << "Valamelyik testresze faj? (1. igen; 2. nem) Varom a valaszat: ";

                cin >> seged2;
                if (seged2 == 1 && sulyossag <= 90) {
                    sulyossag += 10;
                }
                else if (seged2 == 2 && sulyossag > 10) {
                    sulyossag -= 10;
                }

                cout << "Egyeb mellekhatasok? (0. nincs; 1. laz; 2. hanyinger; 3. szedules) Varom a valaszat: ";

                cin >> seged3;

                if (seged3 == 1 && sulyossag <= 90) {
                    sulyossag += 10;
                }
                else if (seged3 == 2 && sulyossag <= 95) {
                    sulyossag += 5;
                }
                else if (seged3 == 3 && sulyossag <= 85) {
                    sulyossag += 15;
                }

                if (sulyossag == 100) {
                    cout << "Sajnaljuk on meghal.";
                    osszstatisztika[1]++;
                    fejstat[1]++;
                    break;
                }

                Varoterem_fej.changePriority(sorszam, sulyossag);
                fejstat[2]++;
            }
            else if (seged4 == 2) {
                if (Varoterem_torzs.getSize() == -1) {
                    cout << "Bocsanat, nem lehetseges ez az opcio." << endl;
                    break;
                }
                cout << "Mutatom a varakozasi listat:" << endl;
                Varoterem_torzs.printQueue();
                cout << "Hanyas sorszama van a listan? Varom a valaszat: ";
                cin >> sorszam;
                sorszam--;
                if (sorszam > Varoterem_torzs.getSize()) {
                    cout << "Bocsanat, nem talaltunk ilyen sorszamu embert a rendeloben." << endl;
                    break;
                }

                sulyossag = rand() % 100 + 1;


                if (sulyossag <= 90) {
                    sulyossag = sulyossag + 10;
                }

                cout << "Valamelyik testresze faj? (1. igen; 2. nem) Varom a valaszat: ";

                cin >> seged2;
                if (seged2 == 1 && sulyossag <= 90) {
                    sulyossag += 10;
                }
                else if (seged2 == 2 && sulyossag > 10) {
                    sulyossag -= 10;
                }

                cout << "Egyeb mellekhatasok? (0. nincs; 1. laz; 2. hanyinger; 3. szedules) Varom a valaszat: ";

                cin >> seged3;

                if (seged3 == 1 && sulyossag <= 90) {
                    sulyossag += 10;
                }
                else if (seged3 == 2 && sulyossag <= 95) {
                    sulyossag += 5;
                }
                else if (seged3 == 3 && sulyossag <= 85) {
                    sulyossag += 15;
                }

                if (sulyossag == 100) {
                    cout << "Sajnaljuk on meghal.";
                    osszstatisztika[1]++;
                    torzsstat[1]++;
                    break;
                }

                Varoterem_torzs.changePriority(sorszam, sulyossag);
                torzsstat[2]++;
            }
            else {
                if (Varoterem_vegtagok.getSize() == -1) {
                    cout << "Bocsanat, nem lehetseges ez az opcio." << endl;
                    break;
                }
                cout << "Mutatom a varakozasi listat:" << endl;
                Varoterem_vegtagok.printQueue();
                cout << "Hanyas sorszama van a listan? Varom a valaszat: ";
                cin >> sorszam;
                sorszam--;
                if (sorszam > Varoterem_vegtagok.getSize()) {
                    cout << "Bocsanat, nem talaltunk ilyen sorszamu embert a rendeloben." << endl;
                    break;
                }

                sulyossag = rand() % 100 + 1;


                if (sulyossag <= 95) {
                    sulyossag = sulyossag + 5;
                }

                cout << "Valamelyik testresze faj? (1. igen; 2. nem) Varom a valaszat: ";

                cin >> seged2;
                if (seged2 == 1 && sulyossag <= 90) {
                    sulyossag += 10;
                }
                else if (seged2 == 2 && sulyossag > 10) {
                    sulyossag -= 10;
                }

                cout << "Egyeb mellekhatasok? (0. nincs; 1. laz; 2. hanyinger; 3. szedules) Varom a valaszat: ";

                cin >> seged3;

                if (seged3 == 1 && sulyossag <= 90) {
                    sulyossag += 10;
                }
                else if (seged3 == 2 && sulyossag <= 95) {
                    sulyossag += 5;
                }
                else if (seged3 == 3 && sulyossag <= 85) {
                    sulyossag += 15;
                }

                if (sulyossag == 100) {
                    cout << "Sajnaljuk on meghal.";
                    osszstatisztika[1]++;
                    vegtagokstat[1]++;
                    break;
                }

                Varoterem_vegtagok.changePriority(sorszam, sulyossag);
                vegtagokstat[2]++;
            }

            osszstatisztika[2]++;

            cout << "Rogzitettuk valaszat." << endl;
            break;
        case 3:
            system("cls");

            cout << "Melyik orvoshoz var?(1. fej; 2. torzs; 3. vegtagok) Varom a valaszat: ";
            cin >> seged4;

            if (seged4 == 1) {
                if (Varoterem_fej.getSize() == -1) {
                    cout << "Bocsanat, nem lehetseges ez az opcio." << endl;
                    break;
                }
                cout << "Sajnaljuk, hogy megunta a varakozast." << endl;
                cout << "Mutatom a varakozasi listat:" << endl;
                Varoterem_fej.printQueue();
                cout << "Hanyas sorszama van a listan? Varom a valaszat: ";
                cin >> sorszam;
                sorszam--;
                if (sorszam > Varoterem_fej.getSize()) {
                    cout << "Bocsanat, nem talaltunk ilyen sorszamu embert a rendeloben." << endl;
                    break;
                }
                Varoterem_fej.remove(sorszam);
                fejstat[5]++;
            }
            else if (seged4 == 2) {
                if (Varoterem_torzs.getSize() == -1) {
                    cout << "Bocsanat, nem lehetseges ez az opcio." << endl;
                    break;
                }
                cout << "Sajnaljuk, hogy megunta a varakozast." << endl;
                cout << "Mutatom a varakozasi listat:" << endl;
                Varoterem_torzs.printQueue();
                cout << "Hanyas sorszama van a listan? Varom a valaszat: ";
                cin >> sorszam;
                sorszam--;
                if (sorszam > Varoterem_torzs.getSize()) {
                    cout << "Bocsanat, nem talaltunk ilyen sorszamu embert a rendeloben." << endl;
                    break;
                }
                Varoterem_torzs.remove(sorszam);
                torzsstat[5]++;
            }
            else {
                if (Varoterem_vegtagok.getSize() == -1) {
                    cout << "Bocsanat, nem lehetseges ez az opcio." << endl;
                    break;
                }
                cout << "Sajnaljuk, hogy megunta a varakozast." << endl;
                cout << "Mutatom a varakozasi listat:" << endl;
                Varoterem_vegtagok.printQueue();
                cout << "Hanyas sorszama van a listan? Varom a valaszat: ";
                cin >> sorszam;
                sorszam--;
                if (sorszam > Varoterem_vegtagok.getSize()) {
                    cout << "Bocsanat, nem talaltunk ilyen sorszamu embert a rendeloben." << endl;
                    break;
                }
                Varoterem_vegtagok.remove(sorszam);
                vegtagokstat[5]++;
            }
           
            osszstatisztika[5]++;
         
            break;
        case 4:
            system("cls");

            cout << "Melyik orvoshoz var?(1. fej; 2. torzs; 3. vegtagok) Varom a valaszat: ";
            cin >> seged4;

            if (seged4 == 1) {
                if (Varoterem_fej.getSize() == -1) {
                    cout << "Bocsanat, nem lehetseges ez az opcio." << endl;
                    break;
                }
                cout << "Johet a kovetkezo beteg. A kovetkezo beteg: ";
                beteg = Varoterem_fej.extractMax();
                cout << beteg.second;
                fejstat[4]++;
            }
            else if (seged4 == 2) {
                if (Varoterem_torzs.getSize() == -1) {
                    cout << "Bocsanat, nem lehetseges ez az opcio." << endl;
                    break;
                }
                cout << "Johet a kovetkezo beteg. A kovetkezo beteg: ";
                beteg = Varoterem_torzs.extractMax();
                cout << beteg.second;
                torzsstat[4]++;
            }
            else {
                if (Varoterem_vegtagok.getSize() == -1) {
                    cout << "Bocsanat, nem lehetseges ez az opcio." << endl;
                    break;
                }
                cout << "Johet a kovetkezo beteg. A kovetkezo beteg: ";
                beteg = Varoterem_vegtagok.extractMax();
                cout << beteg.second;
                vegtagokstat[4]++;
            }
           
            osszstatisztika[4]++;
            break;
        case 5:
            system("cls");
            cout << "Melyik orvoshoz var?(1. fej; 2. torzs; 3. vegtagok) Varom a valaszat: ";
            cin >> seged4;

            if (seged4 == 1) {
                if (Varoterem_fej.getSize() == -1) {
                    cout << "A varolista ures." << endl;
                    break;
                }
                cout << "Mutatom a varolistat:" << endl;
                Varoterem_fej.printQueue();
                fejstat[3]++;
            }
            else if (seged4 == 2) {
                if (Varoterem_torzs.getSize() == -1) {
                    cout << "A varolista ures." << endl;
                    break;
                }
                cout << "Mutatom a varolistat:" << endl;
                Varoterem_torzs.printQueue();
                torzsstat[3]++;
            }
            else {
                if (Varoterem_vegtagok.getSize() == -1) {
                    cout << "A varolista ures." << endl;
                    break;
                }
                cout << "Mutatom a varolistat:" << endl;
                Varoterem_vegtagok.printQueue();
                vegtagokstat[3]++;
            }
            
            osszstatisztika[3]++;
            break;
        case 6:
            system("cls");
            cout << "Melyik orvoshoz var?(1. fej; 2. torzs; 3. vegtagok) Varom a valaszat: ";
            cin >> seged4;

            if (seged4 == 1) {
                if (Varoterem_fej.getSize() == -1) {
                    cout << "Bocsanat, ures a varoterem." << endl;
                    break;
                }
                beteg = Varoterem_fej.getMax();
                cout << "Az orvosnal " << beteg.second << " kovetkezik. " << endl;
                fejstat[6]++;
            }
            else if (seged4 == 2) {
                if (Varoterem_torzs.getSize() == -1) {
                    cout << "Bocsanat, ures a varoterem." << endl;
                    break;
                }
                beteg = Varoterem_torzs.getMax();
                cout << "Az orvosnal " << beteg.second << " kovetkezik. " << endl;
                torzsstat[6]++;
            }
            else {
                if (Varoterem_vegtagok.getSize() == -1) {
                    cout << "Bocsanat, ures a varoterem." << endl;
                    break;
                }
                beteg = Varoterem_vegtagok.getMax();
                cout << "Az orvosnal " << beteg.second << " kovetkezik. " << endl;
                vegtagokstat[6]++;
            }
           
            osszstatisztika[6]++;
            break;
        case 7:
            system("cls");
            cout << "A rendelo mara bezart. Viszontlatasra." << endl;
            cout << endl << "Statisztikak a mai napra:" << endl;
            cout << "A rendelo ma " << osszstatisztika[0] << " beteget vett fel a listara osszesen." << endl;
            cout << "A rendeloben ma " << osszstatisztika[1] << " beteg halalozott el osszesen." << endl;
            cout << "A rendeloben ma " << osszstatisztika[2] << " beteg cserelte sulyossagat osszesen." << endl;
            cout << "A rendelo ma " << osszstatisztika[3] << " alkalommal mutatta a varolistat osszesen." << endl;
            cout << "A rendeloben ma " << osszstatisztika[4] << " beteget kezelt az orvos osszesen." << endl;
            cout << "A rendeloben ma " << osszstatisztika[5] << " beteg unta meg a varakozast osszesen." << endl;
            cout << "A rendeloben ma " << osszstatisztika[6] << " alkalommal kerdeztek ki kovetkezik osszesen." << endl;
            cout << "A rendeloben ma " << osszstatisztika[7] << " beteg vart sulyos ellatasra osszesen." << endl;
            cout << "A rendeloben ma " << osszstatisztika[8] << " beteg vart nem annyira sulyos ellatasra osszesen." << endl;
            cout << "A rendeloben ma " << osszstatisztika[9] << " beteg vart valami papir kiadasara osszesen." << endl;

            cout << endl << "Statisztikak a mai napra a fejorvosnal:" << endl;
            cout << "A fejorvos ma " << fejstat[0] << " beteget vett fel a listara osszesen." << endl;
            cout << "A fejorvosra varva ma " << fejstat[1] << " beteg halalozott el osszesen." << endl;
            cout << "A fejorvosra varva ma " << fejstat[2] << " beteg cserelte sulyossagat osszesen." << endl;
            cout << "A fejorvos ma " << fejstat[3] << " alkalommal mutatta a varolistat osszesen." << endl;
            cout << "A rendeloben ma " << fejstat[4] << " beteget kezelt az fejorvos osszesen." << endl;
            cout << "A fejorvosra varva ma " << fejstat[5] << " beteg unta meg a varakozast osszesen." << endl;
            cout << "A fejorvosra varva ma " << fejstat[6] << " alkalommal kerdeztek ki kovetkezik osszesen." << endl;
            cout << "A fejorvosnal ma " << fejstat[7] << " beteg vart sulyos ellatasra osszesen." << endl;
            cout << "A fejorvosnal ma " << fejstat[8] << " beteg vart nem annyira sulyos ellatasra osszesen." << endl;
            cout << "A fejorvosnal ma " << fejstat[9] << " beteg vart valami papir kiadasara osszesen." << endl;

            cout << endl << "Statisztikak a mai napra a torzsorvosnal:" << endl;
            cout << "A torzsorvos ma " << torzsstat[0] << " beteget vett fel a listara osszesen." << endl;
            cout << "A torzsorvosra varva ma " << torzsstat[1] << " beteg halalozott el osszesen." << endl;
            cout << "A torzsorvosra varva ma " << torzsstat[2] << " beteg cserelte sulyossagat osszesen." << endl;
            cout << "A torzsorvos ma " << torzsstat[3] << " alkalommal mutatta a varolistat osszesen." << endl;
            cout << "A rendeloben ma " << torzsstat[4] << " beteget kezelt az torzsorvos osszesen." << endl;
            cout << "A torzsorvosra varva ma " << torzsstat[5] << " beteg unta meg a varakozast osszesen." << endl;
            cout << "A torzsorvosra varva ma " << torzsstat[6] << " alkalommal kerdeztek ki kovetkezik osszesen." << endl;
            cout << "A torzsorvosnal ma " << torzsstat[7] << " beteg vart sulyos ellatasra osszesen." << endl;
            cout << "A torzsorvosnal ma " << torzsstat[8] << " beteg vart nem annyira sulyos ellatasra osszesen." << endl;
            cout << "A torzsorvosnal ma " << torzsstat[9] << " beteg vart valami papir kiadasara osszesen." << endl;

            cout << endl << "Statisztikak a mai napra a vegtagorvosnal:" << endl;
            cout << "A vegtagorvos ma " << vegtagokstat[0] << " beteget vett fel a listara osszesen." << endl;
            cout << "A vegtagorvosra varva ma " << vegtagokstat[1] << " beteg halalozott el osszesen." << endl;
            cout << "A vegtagorvosra varva ma " << vegtagokstat[2] << " beteg cserelte sulyossagat osszesen." << endl;
            cout << "A vegtagorvos ma " << vegtagokstat[3] << " alkalommal mutatta a varolistat osszesen." << endl;
            cout << "A rendeloben ma " << vegtagokstat[4] << " beteget kezelt az vegtagorvos osszesen." << endl;
            cout << "A vegtagorvosra varva ma " << vegtagokstat[5] << " beteg unta meg a varakozast osszesen." << endl;
            cout << "A vegtagorvosra varva ma " << vegtagokstat[6] << " alkalommal kerdeztek ki kovetkezik osszesen." << endl;
            cout << "A vegtagorvosnal ma " << vegtagokstat[7] << " beteg vart sulyos ellatasra osszesen." << endl;
            cout << "A vegtagorvosnal ma " << vegtagokstat[8] << " beteg vart nem annyira sulyos ellatasra osszesen." << endl;
            cout << "A vegtagorvosnal ma " << vegtagokstat[9] << " beteg vart valami papir kiadasara osszesen." << endl;
            mehet = false;

            break;
        default:
            system("cls");
            cout << "Nincs ilyen opcio." << endl;
            break;
        }
        cout << endl;
    }

    return 0;
}