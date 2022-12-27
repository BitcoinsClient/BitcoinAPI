package de.bitcoinclient.crypter;

public enum CrypterTypes {

    A(414,"A"),
    a(866, "a"),

    B(901,"B"),
    b(664, "b"),

    C(332,"C"),
    c(448, "c"),

    D(546,"D"),
    d(706,"d"),

    E(173,"E"),
    e(582,"e"),

    F(573,"F"),
    f(489,"f"),

    G(209,"G"),
    g(724,"g"),

    H(211,"H"),
    h(580,"h"),

    I(387,"I"),
    i(734,"i"),

    J(899,"J"),
    j(953, "j"),

    K(826,"K"),
    k(299, "k"),

    L(968,"L"),
    l(678, "l"),

    M(954,"M"),
    m(472,"m"),

    N(659,"N"),
    n(950,"n"),

    O(669,"O"),
    o(768,"o"),

    P(955,"P"),
    p(386,"p"),

    Q(232,"Q"),
    q(725,"q"),

    R(586,"R"),
    r(471,"r"),

    S(982,"S"),
    s(237, "s"),

    T(238,"T"),
    t(180,"t"),

    U(359,"U"),
    u(987,"u"),

    V(140,"V"),
    v(188,"v"),

    W(995,"W"),
    w(276,"w"),

    X(683,"X"),
    x(338,"x"),

    Y(532,"Y"),
    y(240,"y"),

    Z(462,"Z"),
    z(838,"z"),

    Ä(317,"Ä"),
    ä(962,"ä"),

    Ü(126,"Ü"),
    ü(519,"ü"),

    Ö(168,"Ö"),
    ö(496,"ö"),

    _0(460,"0"),
    _1(600,"1"),
    _2(437,"2"),
    _3(555,"3"),
    _4(774,"4"),
    _5(316,"5"),
    _6(404,"6"),
    _7(356,"7"),
    _8(994,"8"),
    _9(933,"9"),

    HASHTAG(646,"#"),
    SMCOM(392,";"),
    HYPHEN(922,"-"),
    DOT(562,"."),
    EQUAL(857,"="),
    COLON(478,":"),
    _123(769, "|"),
    SPACE(347, " "),
    AU(197,"!");

    int crypt;
    String letter;
    CrypterTypes(int crypt, String letter) {
        this.crypt = crypt;
        this.letter = letter;
    }

    public int getCrypt() {
        return crypt;
    }

    public String getLetter() {
        return letter;
    }
}
