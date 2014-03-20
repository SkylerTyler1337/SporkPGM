package io.sporkpgm.module;

import io.sporkpgm.module.exceptions.ModuleLoadException;
import io.sporkpgm.util.Log;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.Document;

import com.google.common.collect.Maps;

public class ModuleContainer {
	private HashMap<Module, ModuleInfo> modules = Maps.newHashMap();

	public ModuleContainer(Document doc) {
		for (ModuleInfo info : ModuleRegistry.getModules()) {
			try {
				addModule(info, doc);
			} catch (Exception e) {
				Log.log(e);
			}
		}
	}

	public Module getModule(Class<? extends Module> clazz) {
		for (Module module : modules.keySet())
			if (module.getClass().equals(clazz))
				return module;
		return null;
	}

	public void enableModules() {
		for (Module module : modules.keySet())
			module.enable();
	}

	public void disableModules() {
		for (Module module : modules.keySet())
			module.disable();
	}

	public void addModule(ModuleInfo info, Document doc) throws Exception {
		for (Class<? extends Module> dependency : info.requires()) {
			if (!dependency.isAnnotationPresent(ModuleInfo.class))
				throw new ModuleLoadException("Module " + dependency.getClass().getName() + " does not have @ModuleInfo tag!");
			addModule(dependency.getAnnotation(ModuleInfo.class), doc);
		}
		if (registered(info))
			return;
		Method method = info.module().getMethod("parse", new Class[] { Document.class });
		if (method == null)
			return;
		Module module = (Module) method.invoke(null, new Object[] { doc });
		modules.put(module, info);
	}

	private boolean registered(ModuleInfo info) {
		for (Map.Entry<Module, ModuleInfo> entry : modules.entrySet())
			if (entry.getValue().name().equals(info.name()))
				return true;
		return false;
	}

}
