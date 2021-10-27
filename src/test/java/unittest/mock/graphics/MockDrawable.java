package unittest.mock.graphics;

import tech.fastj.graphics.Drawable;

import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SimpleManager;

public class MockDrawable extends Drawable {
  @Override
  public void destroy(Scene origin) {}

  @Override
  public void destroy(SimpleManager origin) {}
}
