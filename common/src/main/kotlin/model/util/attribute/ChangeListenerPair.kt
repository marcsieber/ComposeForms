package model.util.attribute

class ChangeListenerPair<T>(val attribute: Attribute<*,T?,*>, val func: (a: Attribute<*,Any?,*>, v: T?) -> Unit)