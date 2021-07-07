package tech.fastj.input.keyboard;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Based on the {@link KeyEvent} class, the {@code Keys} class defines better-looking names for keyboard input
 * keycodes.
 *
 * @author Andrew Dey
 * @version 1.5.0
 */
public enum Keys {

    /** Constant representing the "Enter" key. */
    Enter(KeyEvent.VK_ENTER, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Backspace" key. */
    Backspace(KeyEvent.VK_BACK_SPACE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Tab" key. */
    Tab(KeyEvent.VK_TAB, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Cancel" key. */
    Cancel(KeyEvent.VK_CANCEL, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Clear" key. */
    Clear(KeyEvent.VK_CLEAR, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Left Shift" key. */
    LeftShift(KeyEvent.VK_SHIFT, KeyEvent.KEY_LOCATION_LEFT),

    /** Constant representing the "Left Control" key. */
    LeftControl(KeyEvent.VK_CONTROL, KeyEvent.KEY_LOCATION_LEFT),

    /** Constant representing the "Left Alt" key. */
    LeftAlt(KeyEvent.VK_ALT, KeyEvent.KEY_LOCATION_LEFT),

    /** Constant representing the "Right Shift" key. */
    RightShift(KeyEvent.VK_SHIFT, KeyEvent.KEY_LOCATION_RIGHT),

    /** Constant representing the "Right Control" key. */
    RightControl(KeyEvent.VK_CONTROL, KeyEvent.KEY_LOCATION_RIGHT),

    /** Constant representing the "Right Alt" key. */
    RightAlt(KeyEvent.VK_ALT, KeyEvent.KEY_LOCATION_RIGHT),

    /** Constant representing the "Pause" key. */
    Pause(KeyEvent.VK_PAUSE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Caps lock" key. */
    CapsLock(KeyEvent.VK_CAPS_LOCK, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Escape" key. */
    Escape(KeyEvent.VK_ESCAPE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Space" key. */
    Space(KeyEvent.VK_SPACE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Page up" key. */
    PageUp(KeyEvent.VK_PAGE_UP, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Page down" key. */
    PageDown(KeyEvent.VK_PAGE_DOWN, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "End" key. */
    End(KeyEvent.VK_END, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Home" key. */
    Home(KeyEvent.VK_HOME, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Left" key. */
    Left(KeyEvent.VK_LEFT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Up" key. */
    Up(KeyEvent.VK_UP, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Right" key. */
    Right(KeyEvent.VK_RIGHT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Down" key. */
    Down(KeyEvent.VK_DOWN, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Comma" key. */
    Comma(KeyEvent.VK_COMMA, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Minus" key. */
    Minus(KeyEvent.VK_MINUS, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Period" key. */
    Period(KeyEvent.VK_PERIOD, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Slash" key. */
    Slash(KeyEvent.VK_SLASH, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "0" key. */
    Zero(KeyEvent.VK_0, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "1" key. */
    One(KeyEvent.VK_1, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "2" key. */
    Two(KeyEvent.VK_2, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "3" key. */
    Three(KeyEvent.VK_3, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "4" key. */
    Four(KeyEvent.VK_4, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "5" key. */
    Five(KeyEvent.VK_5, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "6" key. */
    Six(KeyEvent.VK_6, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "7" key. */
    Seven(KeyEvent.VK_7, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "8" key. */
    Eight(KeyEvent.VK_8, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "9" key. */
    Nine(KeyEvent.VK_9, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the ";" key. */
    Semicolon(KeyEvent.VK_SEMICOLON, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Equals" key. */
    Equals(KeyEvent.VK_EQUALS, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "A" key. */
    A(KeyEvent.VK_A, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "B" key. */
    B(KeyEvent.VK_B, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "C" key. */
    C(KeyEvent.VK_C, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "D" key. */
    D(KeyEvent.VK_D, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "E" key. */
    E(KeyEvent.VK_E, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F" key. */
    F(KeyEvent.VK_F, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "G" key. */
    G(KeyEvent.VK_G, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "H" key. */
    H(KeyEvent.VK_H, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "I" key. */
    I(KeyEvent.VK_I, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "J" key. */
    J(KeyEvent.VK_J, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "K" key. */
    K(KeyEvent.VK_K, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "L" key. */
    L(KeyEvent.VK_L, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "M" key. */
    M(KeyEvent.VK_M, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "N" key. */
    N(KeyEvent.VK_N, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "O" key. */
    O(KeyEvent.VK_O, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "P" key. */
    P(KeyEvent.VK_P, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Q" key. */
    Q(KeyEvent.VK_Q, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "R" key. */
    R(KeyEvent.VK_R, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "S" key. */
    S(KeyEvent.VK_S, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "T" key. */
    T(KeyEvent.VK_T, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "U" key. */
    U(KeyEvent.VK_U, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "V" key. */
    V(KeyEvent.VK_V, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "W" key. */
    W(KeyEvent.VK_W, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "X" key. */
    X(KeyEvent.VK_X, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Y" key. */
    Y(KeyEvent.VK_Y, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Z" key. */
    Z(KeyEvent.VK_Z, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Open bracket" key. */
    OpenBracket(KeyEvent.VK_OPEN_BRACKET, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Back slash" key. */
    BackSlash(KeyEvent.VK_BACK_SLASH, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Close bracket" key. */
    CloseBracket(KeyEvent.VK_CLOSE_BRACKET, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "NUMPAD" key. */
    Numpad0(KeyEvent.VK_NUMPAD0, KeyEvent.KEY_LOCATION_NUMPAD),

    /** Constant representing the "NUMPAD" key. */
    Numpad1(KeyEvent.VK_NUMPAD1, KeyEvent.KEY_LOCATION_NUMPAD),

    /** Constant representing the "NUMPAD" key. */
    Numpad2(KeyEvent.VK_NUMPAD2, KeyEvent.KEY_LOCATION_NUMPAD),

    /** Constant representing the "NUMPAD" key. */
    Numpad3(KeyEvent.VK_NUMPAD3, KeyEvent.KEY_LOCATION_NUMPAD),

    /** Constant representing the "NUMPAD" key. */
    Numpad4(KeyEvent.VK_NUMPAD4, KeyEvent.KEY_LOCATION_NUMPAD),

    /** Constant representing the "NUMPAD" key. */
    Numpad5(KeyEvent.VK_NUMPAD5, KeyEvent.KEY_LOCATION_NUMPAD),

    /** Constant representing the "NUMPAD" key. */
    Numpad6(KeyEvent.VK_NUMPAD6, KeyEvent.KEY_LOCATION_NUMPAD),

    /** Constant representing the "NUMPAD" key. */
    Numpad7(KeyEvent.VK_NUMPAD7, KeyEvent.KEY_LOCATION_NUMPAD),

    /** Constant representing the "NUMPAD" key. */
    Numpad8(KeyEvent.VK_NUMPAD8, KeyEvent.KEY_LOCATION_NUMPAD),

    /** Constant representing the "NUMPAD" key. */
    Numpad9(KeyEvent.VK_NUMPAD9, KeyEvent.KEY_LOCATION_NUMPAD),

    /** Constant representing the "Multiply" key. */
    Multiply(KeyEvent.VK_MULTIPLY, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Add" key. */
    Add(KeyEvent.VK_ADD, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Separater" key. */
    Separater(KeyEvent.VK_SEPARATER, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Separator" key. */
    Separator(KeyEvent.VK_SEPARATOR, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Subtract" key. */
    Subtract(KeyEvent.VK_SUBTRACT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Decimal" key. */
    Decimal(KeyEvent.VK_DECIMAL, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Divide" key. */
    Divide(KeyEvent.VK_DIVIDE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Delete" key. */
    Delete(KeyEvent.VK_DELETE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Num lock" key. */
    NumLock(KeyEvent.VK_NUM_LOCK, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Scroll lock" key. */
    ScrollLock(KeyEvent.VK_SCROLL_LOCK, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F1" key. */
    F1(KeyEvent.VK_F1, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F2" key. */
    F2(KeyEvent.VK_F2, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F3" key. */
    F3(KeyEvent.VK_F3, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F4" key. */
    F4(KeyEvent.VK_F4, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F5" key. */
    F5(KeyEvent.VK_F5, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F6" key. */
    F6(KeyEvent.VK_F6, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F7" key. */
    F7(KeyEvent.VK_F7, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F8" key. */
    F8(KeyEvent.VK_F8, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F9" key. */
    F9(KeyEvent.VK_F9, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F10" key. */
    F10(KeyEvent.VK_F10, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F11" key. */
    F11(KeyEvent.VK_F11, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F12" key. */
    F12(KeyEvent.VK_F12, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F13" key. */
    F13(KeyEvent.VK_F13, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F14" key. */
    F14(KeyEvent.VK_F14, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F15" key. */
    F15(KeyEvent.VK_F15, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F16" key. */
    F16(KeyEvent.VK_F16, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F17" key. */
    F17(KeyEvent.VK_F17, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F18" key. */
    F18(KeyEvent.VK_F18, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F19" key. */
    F19(KeyEvent.VK_F19, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F20" key. */
    F20(KeyEvent.VK_F20, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F21" key. */
    F21(KeyEvent.VK_F21, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F22" key. */
    F22(KeyEvent.VK_F22, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F23" key. */
    F23(KeyEvent.VK_F23, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "F24" key. */
    F24(KeyEvent.VK_F24, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Print screen" key. */
    PrintScreen(KeyEvent.VK_PRINTSCREEN, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Insert" key. */
    Insert(KeyEvent.VK_INSERT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Help" key. */
    Help(KeyEvent.VK_HELP, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Meta" key. */
    Meta(KeyEvent.VK_META, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Back quote" key. */
    BackQuote(KeyEvent.VK_BACK_QUOTE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Quote" key. */
    Quote(KeyEvent.VK_QUOTE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Kp up" key. */
    KpUp(KeyEvent.VK_KP_UP, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Kp down" key. */
    KpDown(KeyEvent.VK_KP_DOWN, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Kp left" key. */
    KpLeft(KeyEvent.VK_KP_LEFT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Kp right" key. */
    KpRight(KeyEvent.VK_KP_RIGHT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead grave" key. */
    DeadGrave(KeyEvent.VK_DEAD_GRAVE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead acute" key. */
    DeadAcute(KeyEvent.VK_DEAD_ACUTE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead circumflex" key. */
    DeadCircumflex(KeyEvent.VK_DEAD_CIRCUMFLEX, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead tilde" key. */
    DeadTilde(KeyEvent.VK_DEAD_TILDE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead macron" key. */
    DeadMacron(KeyEvent.VK_DEAD_MACRON, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead breve" key. */
    DeadBreve(KeyEvent.VK_DEAD_BREVE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead above dot" key. */
    DeadAboveDot(KeyEvent.VK_DEAD_ABOVEDOT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead diaeresis" key. */
    DeadDiaeresis(KeyEvent.VK_DEAD_DIAERESIS, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead above ring" key. */
    DeadAboveRing(KeyEvent.VK_DEAD_ABOVERING, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead double acute" key. */
    DeadDoubleAcute(KeyEvent.VK_DEAD_DOUBLEACUTE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead caron" key. */
    DeadCaron(KeyEvent.VK_DEAD_CARON, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead cedilla" key. */
    DeadCedilla(KeyEvent.VK_DEAD_CEDILLA, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead ogonek" key. */
    DeadOgonek(KeyEvent.VK_DEAD_OGONEK, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead iota" key. */
    DeadIota(KeyEvent.VK_DEAD_IOTA, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead voiced sound" key. */
    DeadVoicedSound(KeyEvent.VK_DEAD_VOICED_SOUND, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Dead semi-voiced sound" key. */
    DeadSemiVoicedSound(KeyEvent.VK_DEAD_SEMIVOICED_SOUND, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "&amp;" key. */
    Ampersand(KeyEvent.VK_AMPERSAND, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "*" key. */
    Asterisk(KeyEvent.VK_ASTERISK, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Quotedbl" key. */
    Quotedbl(KeyEvent.VK_QUOTEDBL, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Less" key. */
    Less(KeyEvent.VK_LESS, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Greater" key. */
    Greater(KeyEvent.VK_GREATER, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Left bracket" key. */
    LeftBracket(KeyEvent.VK_BRACELEFT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Right bracket" key. */
    RightBracket(KeyEvent.VK_BRACERIGHT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "@" key. */
    AtSign(KeyEvent.VK_AT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the ":" key. */
    Colon(KeyEvent.VK_COLON, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "^" key. */
    Circumflex(KeyEvent.VK_CIRCUMFLEX, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "$" key. */
    DollarSign(KeyEvent.VK_DOLLAR, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Euro currency sign" key. */
    EuroSign(KeyEvent.VK_EURO_SIGN, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "!" key. */
    ExclamationMark(KeyEvent.VK_EXCLAMATION_MARK, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Â¡" key. */
    InvertedExclamationMark(KeyEvent.VK_INVERTED_EXCLAMATION_MARK, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "(" key. */
    LeftParenthesis(KeyEvent.VK_LEFT_PARENTHESIS, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "# (aka hashtag, pound symbol)" key. */
    NumberSign(KeyEvent.VK_NUMBER_SIGN, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "+" key. */
    Plus(KeyEvent.VK_PLUS, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the ")" key. */
    RightParenthesis(KeyEvent.VK_RIGHT_PARENTHESIS, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "_" key. */
    Underscore(KeyEvent.VK_UNDERSCORE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Windows" key. */
    Windows(KeyEvent.VK_WINDOWS, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Context menu" key. */
    ContextMenu(KeyEvent.VK_CONTEXT_MENU, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "final" key. */
    FinalKey(KeyEvent.VK_FINAL, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Convert" key. */
    Convert(KeyEvent.VK_CONVERT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Non-convert" key. */
    NonConvert(KeyEvent.VK_NONCONVERT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Accept" key. */
    Accept(KeyEvent.VK_ACCEPT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Mode change" key. */
    ModeChange(KeyEvent.VK_MODECHANGE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Kana" key. */
    Kana(KeyEvent.VK_KANA, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Kanji" key. */
    Kanji(KeyEvent.VK_KANJI, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Alphanumeric" key. */
    Alphanumeric(KeyEvent.VK_ALPHANUMERIC, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Katakana" key. */
    Katakana(KeyEvent.VK_KATAKANA, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Hiragana" key. */
    Hiragana(KeyEvent.VK_HIRAGANA, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Full width" key. */
    FullWidth(KeyEvent.VK_FULL_WIDTH, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Half width" key. */
    HalfWidth(KeyEvent.VK_HALF_WIDTH, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Roman characters" key. */
    RomanCharacters(KeyEvent.VK_ROMAN_CHARACTERS, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "All candidates" key. */
    AllCandidates(KeyEvent.VK_ALL_CANDIDATES, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Previous candidate" key. */
    PreviousCandidate(KeyEvent.VK_PREVIOUS_CANDIDATE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Code input" key. */
    CodeInput(KeyEvent.VK_CODE_INPUT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Japanese katakana" key. */
    JapaneseKatakana(KeyEvent.VK_JAPANESE_KATAKANA, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Japanese hiragana" key. */
    JapaneseHiragana(KeyEvent.VK_JAPANESE_HIRAGANA, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Japanese roman" key. */
    JapaneseRoman(KeyEvent.VK_JAPANESE_ROMAN, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Kana lock" key. */
    KanaLock(KeyEvent.VK_KANA_LOCK, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Input method on off" key. */
    InputMethodOnOff(KeyEvent.VK_INPUT_METHOD_ON_OFF, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Cut" key. */
    Cut(KeyEvent.VK_CUT, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Copy" key. */
    Copy(KeyEvent.VK_COPY, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Paste" key. */
    Paste(KeyEvent.VK_PASTE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Undo" key. */
    Undo(KeyEvent.VK_UNDO, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Again" key. */
    Again(KeyEvent.VK_AGAIN, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Find" key. */
    Find(KeyEvent.VK_FIND, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Props" key. */
    Props(KeyEvent.VK_PROPS, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Stop" key. */
    Stop(KeyEvent.VK_STOP, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Compose" key. */
    Compose(KeyEvent.VK_COMPOSE, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Alt graph" key. */
    AltGraph(KeyEvent.VK_ALT_GRAPH, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Begin" key. */
    Begin(KeyEvent.VK_BEGIN, KeyEvent.KEY_LOCATION_STANDARD),

    /** Constant representing the "Undefined" key. */
    Undefined(KeyEvent.VK_UNDEFINED, KeyEvent.KEY_LOCATION_STANDARD);

    final int keyCode;
    final int keyLocation;

    Keys(int keyCode, int keyLocation) {
        this.keyCode = keyCode;
        this.keyLocation = keyLocation;
    }

    /**
     * Gets the key code of the specified key.
     *
     * @return The key code, derived from {@link KeyEvent}.
     */
    public int getKeyCode() {
        return keyCode;
    }

    /**
     * Gets the key location of the specified key.
     *
     * @return The key location, derived from {@link KeyEvent}.
     */
    public int getKeyLocation() {
        return keyLocation;
    }

    @Override
    public String toString() {
        return "Keys{" +
                "keyCode=" + keyCode +
                ", keyLocation=" + keyLocation +
                '}';
    }

    /**
     * Attempts to find a key based on the provided {@code keyCode} and {@code keyLocation}.
     * <p>
     * This method is meant for use in situations when trying to find a key while only knowing its key code and location
     * (such as from a {@link KeyEvent}).
     * <p>
     * If possible, store the results of this computation because it searches the entire enum of keys to find the
     * correct key -- this may take longer than expected on slower machines.
     *
     * @param keyName {@code String} name of the key.
     * @return A {@link Keys} enum instance, if one is found. If none is found, a {@link NoSuchElementException} is
     * thrown.
     */
    public static Keys get(String keyName) {
        return Arrays.stream(Keys.values())
                .parallel()
                .filter(keys -> keys.name().equalsIgnoreCase(keyName))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Couldn't find a key with key name \"" + keyName + "\"."));
    }

    /**
     * Attempts to find a key based on the provided {@code keyCode} and {@code keyLocation}.
     * <p>
     * This method is meant for use in situations when trying to find a key while only knowing its key code and location
     * (such as from a {@link KeyEvent}). If possible, store the results of this computation because it searches the
     * entire enum of keys to find the correct key -- this may take longer than expected on slower machines.
     *
     * @param keyEvent The event to evaluate a key for.
     * @return A {@link Keys} enum instance, if one is found. If none is found, a {@link NoSuchElementException} is
     * thrown.
     */
    public static Keys get(KeyEvent keyEvent) {
        return get(KeyEvent.getKeyText(keyEvent.getExtendedKeyCode()));
    }
}
