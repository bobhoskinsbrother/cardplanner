var clover = new Object();

// JSON: {classes : [{name, id, sl, el,  methods : [{sl, el}, ...]}, ...]}
clover.pageData = {"classes":[{"el":46,"id":2122,"methods":[{"el":33,"sc":5,"sl":30},{"el":41,"sc":5,"sl":35},{"el":45,"sc":5,"sl":43}],"name":"EqualsWithDelta","sl":22}]}

// JSON: {test_ID : {"methods": [ID1, ID2, ID3...], "name" : "testXXX() void"}, ...};
clover.testTargets = {"test_222":{"methods":[{"sl":30},{"sl":35}],"name":"equalsWithDelta","pass":true,"statements":[{"sl":31},{"sl":32},{"sl":36},{"sl":37}]},"test_348":{"methods":[{"sl":30},{"sl":43}],"name":"equalsWithDeltaToString","pass":true,"statements":[{"sl":31},{"sl":32},{"sl":44}]},"test_948":{"methods":[{"sl":30},{"sl":35}],"name":"equalsWithDelta","pass":true,"statements":[{"sl":31},{"sl":32},{"sl":36},{"sl":37}]},"test_954":{"methods":[{"sl":30},{"sl":43}],"name":"equalsWithDeltaToString","pass":true,"statements":[{"sl":31},{"sl":32},{"sl":44}]}}

// JSON: { lines : [{tests : [testid1, testid2, testid3, ...]}, ...]};
clover.srcFileLines = [[], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [], [222, 954, 948, 348], [222, 954, 948, 348], [222, 954, 948, 348], [], [], [222, 948], [222, 948], [222, 948], [], [], [], [], [], [954, 348], [954, 348], [], []]
