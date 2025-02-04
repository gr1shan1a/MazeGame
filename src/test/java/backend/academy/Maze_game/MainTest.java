package backend.academy.Maze_game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class MainTest {

    private Generator primGenerator;
    private Generator kruskalGenerator;
    private Renderer consoleRenderer;

    @BeforeEach
    public void setUp() {
        primGenerator = new PrimGenerator();
        kruskalGenerator = new KruskalGenerator();
        consoleRenderer = new ConsoleRenderer();
    }

    @Test
    public void testPrimGenerator() {
        int height = 10;
        int width = 10;
        Maze maze = primGenerator.generate(height, width);

        assertNotNull(maze, "Maze should not be null");
        assertEquals(height, maze.height(), "Maze height should be " + height);
        assertEquals(width, maze.width(), "Maze width should be " + width);

        // Проверка, что начальная и конечная точки установлены
        assertNotNull(maze.start(), "Start point should not be null");
        assertNotNull(maze.end(), "End point should not be null");

        // Проверка, что начальная и конечная точки разные
        assertNotEquals(maze.start(), maze.end(), "Start and end points should not be the same");

        // Проверка, что лабиринт содержит проходы и стены
        boolean hasPassage = false;
        boolean hasWall = false;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                    hasPassage = true;
                }
                if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    hasWall = true;
                }
            }
        }
        assertTrue(hasPassage, "Maze should contain passages");
        assertTrue(hasWall, "Maze should contain walls");
    }

    @Test
    public void testKruskalGenerator() {
        int height = 10;
        int width = 10;
        Maze maze = kruskalGenerator.generate(height, width);

        assertNotNull(maze, "Maze should not be null");
        assertEquals(height, maze.height(), "Maze height should be " + height);
        assertEquals(width, maze.width(), "Maze width should be " + width);

        // Проверка, что начальная и конечная точки установлены
        assertNotNull(maze.start(), "Start point should not be null");
        assertNotNull(maze.end(), "End point should not be null");

        // Проверка, что начальная и конечная точки разные
        assertNotEquals(maze.start(), maze.end(), "Start and end points should not be the same");

        // Проверка, что лабиринт содержит проходы и стены
        boolean hasPassage = false;
        boolean hasWall = false;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                    hasPassage = true;
                }
                if (maze.getCell(row, col).type() == Cell.Type.WALL) {
                    hasWall = true;
                }
            }
        }
        assertTrue(hasPassage, "Maze should contain passages");
        assertTrue(hasWall, "Maze should contain walls");
    }


    @Test
    public void testConsoleRenderer() {
        int height = 10;
        int width = 10;
        Maze maze = primGenerator.generate(height, width);

        String renderedMaze = consoleRenderer.render(maze);

        assertNotNull(renderedMaze, "Rendered maze should not be null");
        assertTrue(renderedMaze.contains("A"), "Rendered maze should contain start point");
        assertTrue(renderedMaze.contains("B"), "Rendered maze should contain end point");
    }

    @Test
    public void testPrivateConstructor() throws Exception {
        var constructor = Main.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThrows(InvocationTargetException.class, constructor::newInstance);
    }

//    @Test
//    public void testInvalidGeneratorChoice() {
//        String input = "3\n10\n10"; // Некорректное значение генератора
//        System.setIn(new ByteArrayInputStream(input.getBytes()));
//
//        // Ловим вывод
//        var outputStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outputStream));
//        Main.main(new String[0]);
//
//        // Проверяем, что в выводе присутствует сообщение о выборе PrimGenerator по умолчанию
//        String output = outputStream.toString();
//        assertTrue(output.contains("выбран генератор Прима по умолчанию"),
//            "PrimGenerator should be selected by default.");
//    }

    @Test
    public void testInvalidMazeSize() {
        String input = "1\n-10\n10"; // Некорректное значение размера лабиринта
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Main.main(new String[0]);
        });

        assertEquals("Maze dimensions must be positive", exception.getMessage());
    }



}
