import org.junit.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class LibraryTest {
    @Test
    public void testSomeLibraryMethod() {
        Library library = new Library();
        assertThat(library.someLibraryMethod()).isTrue();
    }
}
