package unittest.mock.systems.tags;

import tech.fastj.graphics.Drawable;

import tech.fastj.systems.control.Scene;
import tech.fastj.systems.control.SimpleManager;

public class MockTaggableEntity extends Drawable {
  @Override
  public void destroy(Scene origin) {}

  @Override
  public void destroy(SimpleManager origin) {}
}
